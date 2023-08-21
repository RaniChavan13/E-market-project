package com.springboot.emarket.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.springboot.emarket.dao.CustomerDao;
import com.springboot.emarket.dto.Customer;
import com.springboot.emarket.dto.Item;
import com.springboot.emarket.dto.Payment;
import com.springboot.emarket.dto.Product;
import com.springboot.emarket.dto.ShoppingCart;
import com.springboot.emarket.dto.ShoppingOrder;
import com.springboot.emarket.dto.Wishlist;
import com.springboot.emarket.helper.Login;
import com.springboot.emarket.helper.SendMail;
import com.springboot.emarket.repository.PaymentRepository;
import com.springboot.emarket.repository.ProductRepository;
import com.springboot.emarket.repository.ShoppingCartRepository;
import com.springboot.emarket.repository.WishlistRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CustomerService {
	@Autowired
	CustomerDao customerDao;

	@Autowired
	ShoppingCartRepository cartRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	SendMail mail;

	@Autowired
	Item item;

	@Autowired
	WishlistRepository wishlistRepository;

	@Autowired
	PaymentRepository paymentRepository;

	public String signup(Customer customer, String date, ModelMap model) {
		// TODO Auto-generated method stub
		customer.setDob(LocalDate.parse(date));
		if (customerDao.findByEmail(customer.getEmail()) != null
				|| customerDao.findByMobile(customer.getMobile()) != null) {
			model.put("fail", "Email or Mobile should not be repeated");
			return "CustomerSignup";
		}
		String token = "ekart" + new Random().nextInt(10000, 999999);
		customer.setToken(token);

		if (mail.sendLink(customer)) {
			customerDao.save(customer);
			model.put("pass", "Verification Link sent to Email click it to create account");
			return "CustomerLogin";
		} else {
			model.put("fail", "Something went Wrong, Check email and try again");
			return "CustomerSignup";
		}
	}

	public String verifyLink(String email, String token, ModelMap model) {
		// TODO Auto-generated method stub
		Customer customer = customerDao.findByEmail(email);
		if (customer.getToken().equals(token)) {
			customer.setStatus(true);
			customer.setToken(null);
			customerDao.save(customer);
			model.put("pass", "Account Crreated Successfully");
			return "CustomerLogin";
		} else {
			model.put("fail", "Incorrect Link");
			return "CustomerLogin";
		}
	}

	public String login(Login login, HttpSession session, ModelMap model) {
		Customer customer = customerDao.findByEmail(login.getEmail());
		if (customer == null) {
			model.put("fail", "Incorrect Email");
			return "CustomerLogin";
		} else {
			if (customer.getPassword().equals(login.getPassword())) {
				if (customer.isStatus()) {
					session.setAttribute("customer", customer);
					model.put("pass", "Login Success");
					return "CustomerHome";
				} else {
					model.put("fail", "Mail verification Pending, Click on Forgot Password and verify otp");
					return "CustomerLogin";
				}
			} else {
				model.put("fail", "Incorrect Password");
				return "CustomerLogin";
			}
		}
	}

	public String forgotLink(String email, ModelMap model) {
		Customer customer = customerDao.findByEmail(email);
		if (customer == null) {
			model.put("fail", "Incorrect Email");
			return "CustomerForgotPassword";
		} else {
			String token = "ekart" + new Random().nextInt(10000, 999999);
			customer.setToken(token);
			customerDao.save(customer);
			if (mail.sendResetLink(customer)) {
				model.put("pass", "Verification Link sent to Email click it to create account");
				return "CustomerLogin";
			} else {
				model.put("fail", "Something went Wrong, Check email and try again");
				return "CustomerSigup";
			}
		}
	}

	public String resetPassword(String email, String token, ModelMap model) {
		Customer customer = customerDao.findByEmail(email);
		if (customer.getToken().equals(token)) {
			customer.setStatus(true);
			customer.setToken(null);
			model.put("customer", customerDao.save(customer));
			return "CustomrResetPassword";
		} else {
			model.put("fail", "Something went wrong");
			return "CustomerLogin";
		}
	}

	public String setPassword(String email, String password, ModelMap model) {
		Customer customer = customerDao.findByEmail(email);
		customer.setPassword(password);
		customerDao.save(customer);

		model.put("pass", "Password Reset Success");
		return "CustomerLogin";
	}

	public String fetchProducts(ModelMap model, HttpSession session) {
		// TODO Auto-generated method stub
		List<Product> list = productRepository.findByStatus(true);
		if (session.getAttribute("customer") == null) {
			model.put("fail", "Session expired Login Again");
			return "customerLogin";
		} else {
			if (list.isEmpty()) {
				model.put("fail", "no Products present");
				return "CustomerHome";
			} else {
				model.put("products", list);
				return "CustomerDisplayProducts";
			}
		}
	}

	public String addToCart(ModelMap model, HttpSession session, int id) {
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "First Login to Add Product to Cart");
			return "CustomerLogin";
		} else {
			Product product = productRepository.findById(id).orElse(null);
			if (product.getStock() >= 1) {
				ShoppingCart cart = customer.getShoppingCart();
				if (cart == null) {
					cart = new ShoppingCart();
				}

				List<Item> items = cart.getItems();

				if (items == null) {
					items = new ArrayList<Item>();
				}

				if (items.isEmpty()) {
					item.setName(product.getName());
					item.setPrice(product.getPrice());
					item.setQuantity(1);
					item.setDescription(product.getDescription());
					item.setImage(product.getImage());
					items.add(item);
				} else {
					boolean flag = false;
					for (Item item : items) {
						if (item.getName().equals(product.getName())) {
							item.setQuantity(item.getQuantity() + 1);
							item.setPrice(item.getPrice() + product.getPrice());
							item.setDescription(product.getDescription());
							item.setImage(product.getImage());
							flag = false;
							break;
						} else {
							flag = true;
						}
					}
					if (flag) {
						item.setName(product.getName());
						item.setPrice(product.getPrice());
						item.setQuantity(1);
						item.setDescription(product.getDescription());
						item.setImage(product.getImage());
						items.add(item);
					}

				}

				cart.setItems(items);
				customer.setShoppingCart(cart);

				product.setStock(product.getStock() - 1);
				productRepository.save(product);

				session.removeAttribute("customer");
				session.setAttribute("customer", customerDao.save(customer));

				model.put("pass", "Product Added to Cart Success");
				return "CustomerHome";
			} else {
				model.put("fail", "Out of Stock");
				return "CustomerHome";
			}
		}
	}

	public String viewCart(ModelMap model, HttpSession session) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "First Login to View cart");
			return "CustomerLogin";
		} else {
			if (customer.getShoppingCart() == null || customer.getShoppingCart().getItems() == null
					|| customer.getShoppingCart().getItems().isEmpty()) {
				model.put("fail", "No items Found");
				return "CustomerHome";
			} else {
				List<Item> items = customer.getShoppingCart().getItems();
				model.put("items", items);
				return "CustomerDisplayCart";
			}
		}
	}

	public String removeFromCart(HttpSession session, ModelMap model, int id) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "Invalid session");
			return "CustomerLogin";
		} else {
			List<Item> items = customer.getShoppingCart().getItems();
			Item item = null;
			boolean flag = false;
			for (Item item1 : items) {
				if (item1.getId() == id) {
					item = item1;
					if (item1.getQuantity() > 1) {
						item1.setPrice(item1.getPrice() - (item1.getPrice() / item1.getQuantity()));
						item1.setQuantity(item1.getQuantity() - 1);
						break;
					} else {
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				items.remove(item);
			}

			Product product = productRepository.findByName(item.getName());
			product.setStock(product.getStock() + 1);
			productRepository.save(product);

			session.removeAttribute("customer");
			session.setAttribute("customer", customerDao.save(customer));

			model.put("pass", "Product Removed from Cart Success");
			return "CustomerHome";
		}
	}

	public String loadWishlist(ModelMap model, HttpSession session, int id) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "first login to add product to Wishlist");
			return "CustomerLogin";
		} else {
			model.put("id", id);
			model.put("wishlists", customer.getWishlists());
			return "SelectWishlist";
		}
	}

	public String gotoWishlist(ModelMap model, HttpSession session, int id) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "First Login to create Wishlist");
			return "CustomerLogin";
		} else {
			model.put("id", id);
			return "CreateWishlist";
		}
	}

	public String createWishlist(ModelMap model, HttpSession session, int id, String name) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "First login to create wishLisr");
			return "CustomerLogin";
		} else {
			if (wishlistRepository.findByName(name) == null) {
				Wishlist wishlist = new Wishlist();
				wishlist.setName(name);

				Product product = productRepository.findById(id).orElse(null);
				List<Wishlist> list = customer.getWishlists();
				if (list == null) {
					list = new ArrayList<>();
				}

				if (product != null) {
					List<Product> products = new ArrayList<>();
					products.add(product);
					wishlist.setProducts(products);

					list.add(wishlist);

					customer.setWishlists(list);

					session.removeAttribute("customer");
					session.setAttribute("customer", customerDao.save(customer));

					model.put("pass", "Wishlist creation success and product added to wishlist");
				} else {
					list.add(wishlist);

					customer.setWishlists(list);
					session.removeAttribute("customer");
					session.setAttribute("customer", customerDao.save(customer));

					model.put("pass", "wishlist creation success");
				}
				return "CustomerHome";
			} else {
				model.put("fail", "WishList Already exist");
				return "Customerhome";
			}
		}
	}

	public String viewWishlist(ModelMap model, HttpSession session) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "first login to view wishlist");
			return "CustomerLogin";
		} else {
			List<Wishlist> list = customer.getWishlists();
			if (list == null || list.isEmpty()) {
				model.put("fail", "No Wishlist Found");
				return "WishlistHome";
			} else {
				model.put("list", list);
				return "ViewWishlist";
			}
		}
	}

	public String viewWishlistProducts(int id, ModelMap model, HttpSession session) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "First Login to view Wishlist");
			return "CustomerLogin";
		} else {
			Wishlist wishlist = wishlistRepository.findById(id).orElse(null);
			if (wishlist.getProducts() == null || wishlist.getProducts().isEmpty()) {
				model.put("fail", "No items present");
				return "WishlistHome";
			} else {
				model.put("id", wishlist.getId());
				model.put("list", wishlist.getProducts());
				return "ViewWishlistProducts";
			}
		}
	}

	public String addToWishList(ModelMap model, HttpSession session, int wid, int pid) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "First Login to view Wishlist");
			return "CustomerLogin";
		} else {
			Wishlist wishlist = wishlistRepository.findById(wid).orElse(null);
			Product product = productRepository.findById(pid).orElse(null);

			List<Product> list = wishlist.getProducts();
			if (list == null) {
				list = new ArrayList<>();
			}
			boolean flag = true;
			for (Product product2 : list) {
				if (product2 == product) {
					flag = false;
					break;
				}
			}
			if (flag) {
				list.add(product);

				wishlist.setProducts(list);
				wishlistRepository.save(wishlist);

				model.put("pass", "item added to wish list");

			} else {
				model.put("pass", "Item already ecists in wishlist");
			}
			return "WishlistHome";
		}
	}

	public String removeFromWishList(ModelMap model, HttpSession session, int wid, int pid) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "First Login to view Wishlist");
			return "CustomerLogin";
		} else {
			Wishlist wishlist = wishlistRepository.findById(wid).orElse(null);
			Product product = productRepository.findById(pid).orElse(null);
			wishlist.getProducts().remove(product);
			wishlistRepository.save(wishlist);

			model.put("pass", "item removed from wish list");
			return "WishlistHome";
		}
	}

	public String removeWishList(ModelMap model, HttpSession session, int wid) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "First Login to view Wishlist");
			return "CustomerLogin";
		} else {
			Wishlist wishlist = wishlistRepository.findById(wid).orElse(null);
			Wishlist wishlist2 = null;
			for (Wishlist wishlist3 : customer.getWishlists()) {
				if (wishlist3.getName().equals(wishlist.getName())) {
					wishlist2 = wishlist3;
				}
			}

			customer.getWishlists().remove(wishlist2);
			session.setAttribute("customer", customerDao.save(customer));
			wishlistRepository.delete(wishlist);

			model.put("pass", "Wishlist deleted Success");
			return "WishlistHome";
		}
	}

	public String checkPayment(ModelMap model, HttpSession session) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "First Login to view Wishlist");
			return "CustomerLogin";
		} else {
			List<Payment> payments = paymentRepository.findAll();
			if (payments.isEmpty()) {
				model.put("fail", "Sorry you can not place order, There is an internal error try after some time");
				return "CustomerHome";
			} else {
				model.put("list", payments);
				return "SelectPaymentOption";
			}
		}
	}

	public String checkAddress(ModelMap model, HttpSession session, int pid) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "First Login to view Wishlist");
			return "CustomerLogin";
		} else {
			model.put("address", customer.getAddress());
			model.put("pid", pid);
			return "OrderAddress";
		}
	}

	public String submitOrder(ModelMap model, HttpSession session, int pid, String address) {
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "First Login to view Wishlist");
			return "CustomerLogin";
		} else {
			Payment payment = paymentRepository.findById(pid).orElse(null);
			ShoppingOrder order = new ShoppingOrder();
			order.setAddress(address);
			order.setPaymentMode(payment.getName());
			order.setDeliveryDate(LocalDate.now().plusDays(3));

			ShoppingCart cart = customer.getShoppingCart();
			if (cart == null) {
				model.put("fail", "First add items");
				return "CustomerHome";
			}
			if (cart.getItems() == null || cart.getItems().isEmpty()) {
				model.put("fail", "First add items");
				return "CustomerHome";
			}
			double total = 0;
			for (Item item : cart.getItems()) {
				total = total + item.getPrice();
			}
			order.setTotalPrice(total);
			order.setItems(cart.getItems());
			List<ShoppingOrder> list = customer.getOrders();
			if (list == null) {
				list = new ArrayList<>();
			}
			list.add(order);
			customer.setOrders(list);
			customer.setAddress(address);
			cart.setItems(null);
			customer.setShoppingCart(null);
			Customer customer1 = customerDao.save(customer);
			cartRepository.delete(cart);
			session.removeAttribute("customer");
			session.setAttribute("customer", customer1);

			model.put("order", order);
			model.put("customer", customer1);
			model.put("pass", "Order Placed Success");
			return "PrintRecipt";
		}
	}

	public String viewOrders(ModelMap model, HttpSession session) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			model.put("fail", "First Login to view Wishlist");
			return "CustomerLogin";
		} else {
			List<ShoppingOrder> list = customer.getOrders();
			if (list == null || list.isEmpty()) {
				model.put("fail", "No Orders yet");
				return "CustomerHome";
			} else {
				model.put("orders", customer.getOrders());
				return "ViewOrders";
			}
		}
	}

}
