import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;


/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem
{
	ArrayList<Product>  products = new ArrayList<Product>();
	ArrayList<Customer> customers = new ArrayList<Customer>();	

	ArrayList<ProductOrder> orders  = new ArrayList<ProductOrder>();
	ArrayList<ProductOrder> shippedOrders = new ArrayList<ProductOrder>();
	
	ArrayList<CartItem> carts = new ArrayList<CartItem>();

	// These variables are used to generate order numbers, customer id's, product id's 
	int orderNumber = 500;
	int customerId = 900;
	int productId = 700;

	// General variable used to store an error message when something is invalid (e.g. customer id does not exist)  
	String errMsg = null;

	// Random number generator
	Random random = new Random();

	public ECommerceSystem()
	{
		// NOTE: do not modify or add to these objects!! - the TAs will use for testing
		// If you do the class Shoes bonus, you may add shoe products

		// Create some products


		products.add(new Product("Acer Laptop", generateProductId(), 989.0, 99, Product.Category.COMPUTERS));
		products.add(new Product("Apex Desk", generateProductId(), 1378.0, 12, Product.Category.FURNITURE));
		products.add(new Book("Book", generateProductId(), 45.0, 4, 2, "Ahm Gonna Make You Learn", "T. McInerney", 2021));
		products.add(new Product("DadBod Jeans", generateProductId(), 24.0, 50, Product.Category.CLOTHING));
		products.add(new Product("Polo High Socks", generateProductId(), 5.0, 199, Product.Category.CLOTHING));
		products.add(new Product("Tightie Whities", generateProductId(), 15.0, 99, Product.Category.CLOTHING));
		products.add(new Book("Book", generateProductId(), 35.0, 4, 2, "How to Fool Your Prof", "D. Umbast", 1997));
		products.add(new Book("Book", generateProductId(), 45.0, 4, 2, "How to Escape from Prison", "A. Fugitive", 1963));
		products.add(new Book("Book", generateProductId(), 45.0, 4, 2, "How to Teach Programming", "T. McInerney", 2001));
		products.add(new Product("Rock Hammer", generateProductId(), 10.0, 22, Product.Category.GENERAL));
		products.add(new Book("Book", generateProductId(), 45.0, 4, 2, "Ahm Gonna Make You Learn More", "T. McInerney", 2022));
		int[][] stockCounts = {{4, 2}, {3, 5}, {1, 4,}, {2, 3}, {4, 2}};
		products.add(new Shoes("Prada", generateProductId(), 595.0, stockCounts));

		// Create some customers
		customers.add(new Customer(generateCustomerId(),"Inigo Montoya", "1 SwordMaker Lane, Florin"));
		customers.add(new Customer(generateCustomerId(),"Prince Humperdinck", "The Castle, Florin"));
		customers.add(new Customer(generateCustomerId(),"Andy Dufresne", "Shawshank Prison, Maine"));
		customers.add(new Customer(generateCustomerId(),"Ferris Bueller", "4160 Country Club Drive, Long Beach"));
	}

	

	private String generateOrderNumber()
	{
		return "" + orderNumber++;
	}

	private String generateCustomerId()
	{
		return "" + customerId++;
	}

	private String generateProductId()
	{
		return "" + productId++;
	}

	public String getErrorMessage()
	{
		return errMsg;
	}

	public void printCart()
	{
		for (CartItem c : carts)
			c.print();
	}

	public void printAllProducts()
	{
		for (Product p : products)
			p.print();
	}

	public void printAllBooks()
	{
		for (Product p : products)
		{
			if (p.getCategory() == Product.Category.BOOKS)
				p.print();
		}
	}

	public ArrayList<Book> booksByAuthor(String author)
	{
		ArrayList<Book> books = new ArrayList<Book>();
		for (Product p : products)
		{
			if (p.getCategory() == Product.Category.BOOKS)
			{
				Book book = (Book) p;
				if (book.getAuthor().equals(author))
					books.add(book);
			}
		}
		return books;
	}

	public void printAllOrders()
	{
		for (ProductOrder o : orders)
			o.print();
	}

	public void printAllShippedOrders()
	{
		for (ProductOrder o : shippedOrders)
			o.print();
	}

	public void printCustomers()
	{
		for (Customer c : customers)
			c.print();
	}
	/*
	 * Given a customer id, print all the current orders and shipped orders for them (if any)
	 */
	public void printOrderHistory(String customerId)
	{
		
		// Make sure customer exists
		int index = customers.indexOf(new Customer(customerId));
		if (index == -1)
		{
			//throws an exception if customer id is not recognized 
			UnknownCustomerException exception = new UnknownCustomerException("Customer" + customerId + "not found");
			throw exception;
			//errMsg = "Customer " + customerId + " Not Found";
			//return false;
		}	
		System.out.println("Current Orders of Customer " + customerId);
		for (ProductOrder order: orders)
		{
			if (order.getCustomer().getId().equals(customerId))
				order.print();
		}
		System.out.println("\nShipped Orders of Customer " + customerId);
		for (ProductOrder order: shippedOrders)
		{
			if (order.getCustomer().getId().equals(customerId))
				order.print();
		}
	}

	public String orderProduct(String productId, String customerId, String productOptions)
	{
		// Get customer
		int index = customers.indexOf(new Customer(customerId));
		if (index == -1)
		{
			//throws an exception if customer id is not recognized
			UnknownCustomerException exception = new UnknownCustomerException("Customer" + customerId + "not found");
			throw exception;
		}
		Customer customer = customers.get(index);

		// Get product 
		index = products.indexOf(new Product(productId));
		if (index == -1)
		{
			//throws an exception if product id is not found 
			UnknownProductException exception = new UnknownProductException("Product " + productId + " Not Found");
			throw exception;
		}
		Product product = products.get(index);

		// Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
		if (!product.validOptions(productOptions))
		{
			//throws an exception if product options is not valid
			InvalidProductOptionsException exception = new InvalidProductOptionsException("Product " + product.getName() + " ProductId " + productId + " Invalid Options: " + productOptions);
			throw exception;
		}
		// Is it in stock?
		if (product.getStockCount(productOptions) == 0)
		{
			//throws and exception if the product is out of stock
			ProductOutOfStockException exception = new ProductOutOfStockException("Product " + product.getName() + " ProductId " + productId + " Out of Stock");
			throw exception;
		}
		// Create a ProductOrder
		ProductOrder order = new ProductOrder(generateOrderNumber(), product, customer, productOptions);
		product.reduceStockCount(productOptions);

		// Add to orders and return
		orders.add(order);

		return order.getOrderNumber();
	}

	public void stats(String productId, String customerId)
	{
		// Get customer
		int index = customers.indexOf(new Customer(customerId));
		if (index == -1)
		{
			//throws an exception if customer id is not recognized
			UnknownCustomerException exception = new UnknownCustomerException("Customer" + customerId + "not found");
			throw exception;
		}
		Customer customer = customers.get(index);

		// Get product 
		index = products.indexOf(new Product(productId));
		if (index == -1)
		{
			//throws an exception if product id is not found 
			UnknownProductException exception = new UnknownProductException("Product " + productId + " Not Found");
			throw exception;
		}
		Product product = products.get(index);

		//Map that takes in a product as a key and an integer as a value
		Map<Product, Integer> stats = new TreeMap<Product, Integer>();

		//enters a prduct and defaultsthe number of orders to 0
		stats.put(product, 0);


	}


	public void addToCart(String productId, String customerId, String productOptions)
	{
		// Get customer
		int index = customers.indexOf(new Customer(customerId));
		if (index == -1)
		{
			//throws an exception if customer id is not recognized
			UnknownCustomerException exception = new UnknownCustomerException("Customer" + customerId + "not found");
			throw exception;
		}
		Customer customer = customers.get(index);

		// Get product 
		index = products.indexOf(new Product(productId));
		if (index == -1)
		{
			//throws an exception if product id is not found 
			UnknownProductException exception = new UnknownProductException("Product " + productId + " Not Found");
			throw exception;
		}
		Product product = products.get(index);

		// Check if the options are valid for this product 
		if (!product.validOptions(productOptions))
		{
			//throws an exception if product options is not valid
			InvalidProductOptionsException exception = new InvalidProductOptionsException("Product " + product.getName() + " ProductId " + productId + " Invalid Options: " + productOptions);
			throw exception;
		}

		CartItem cartItem = new CartItem(product);
		carts.add(cartItem);
	}


	public void remCartItem(String productId, String customerId)
	{
		// Get customer
		int index = customers.indexOf(new Customer(customerId));
		if (index == -1)
		{
			//throws an exception if customer id is not recognized
			UnknownCustomerException exception = new UnknownCustomerException("Customer" + customerId + "not found");
			throw exception;
		}
		Customer customer = customers.get(index);

		// Get product 
		index = products.indexOf(new Product(productId));
		if (index == -1)
		{
			//throws an exception if product id is not found 
			UnknownProductException exception = new UnknownProductException("Product " + productId + " Not Found");
			throw exception;
		}

		//checks to see if the item is in the cart, if it is then it will be removed from the cart and returns the item
		//removes the last item in the cart
		Product product = products.get(index);

		CartItem cartItem = new CartItem(product);

		carts.remove(cartItem);
	}



	
	public String orderItems(String customerId)
	{
		// Get customer
		int index = customers.indexOf(new Customer(customerId));
		if (index == -1)
		{
			//throws an exception if customer id is not recognized
			UnknownCustomerException exception = new UnknownCustomerException("Customer" + customerId + "not found");
			throw exception;
		}
		Customer customer = customers.get(index);

		String orderNumbers = "";
		for(CartItem i : carts)
		{
			Product p = i.getProduct();
			index = products.indexOf(new Product(p.getId()));
			if (index == -1)
			{
				//throws an exception if product id is not found 
			UnknownProductException exception = new UnknownProductException("Product " + productId + " Not Found");
			throw exception;
			}

			// Create a ProductOrder
			ProductOrder order = new ProductOrder(generateOrderNumber(), p, customer, "");
			p.reduceStockCount("");

			// Add to orders and return
			orders.add(order);

			//removes the item from the cart
			carts.remove(i);


			//stores the ordernumber of each product order 
			orderNumbers += (" " + order.getOrderNumber());
		}

		//returns the ordernumbers of the product orders
		return orderNumbers;

	}

	public void rate(String productId, String customerId, String rate)
	{
		int index = customers.indexOf(new Customer(customerId));
		if (index == -1)
		{
			//throws an exception if customer id is not recognized
			UnknownCustomerException exception = new UnknownCustomerException("Customer" + customerId + "not found");
			throw exception;
		}
		Customer customer = customers.get(index);

		index = products.indexOf(new Product(productId));
		if (index == -1)
		{
			//throws an exception if product id is not found 
			UnknownProductException exception = new UnknownProductException("Product " + productId + " Not Found");
			throw exception;
		}

		//creates a treemap which uses the users entered product id as the key and the rating they entered as the value
		Product product = products.get(index);
		Map<String, String> ratings = new TreeMap<String, String>();
		//takes both and enters them into the map
		ratings.put(productId,rate);

		//prints the key and value of the map using a for loop and entry set
		for(Map.Entry<String, String> s:ratings.entrySet())
		System.out.println(s.getValue());
	}


	
	/*
	 * Create a new Customer object and add it to the list of customers
	 */

	public void createCustomer(String name, String address)
	{
		// Check to ensure name is valid
		if (name == null || name.equals(""))
		{
			InvalidCustomerNameException exception = new InvalidCustomerNameException("Invalid Customer Name " + name);
			throw exception;
		}
		// Check to ensure address is valid
		if (address == null || address.equals(""))
		{
			InvalidCustomerAddressException exception = new InvalidCustomerAddressException("Invalid Customer Address " + address);
			throw exception;
		}
		Customer customer = new Customer(generateCustomerId(), name, address);
		customers.add(customer);
	}

	public ProductOrder shipOrder(String orderNumber)
	{
		// Check if order number exists
		int index = orders.indexOf(new ProductOrder(orderNumber,null,null,""));
		if (index == -1)
		{
			//throwns an exception if ordernumber is not found
			errMsg = "Order " + orderNumber + " Not Found";
			InvalidOrderNumberException exception = new InvalidOrderNumberException("Order " + orderNumber + " Not Found");
			throw exception;
		}
		ProductOrder order = orders.get(index);
		orders.remove(index);
		shippedOrders.add(order);
		return order;
	}

	/*
	 * Cancel a specific order based on order number
	 */
	public void cancelOrder(String orderNumber)
	{
		// Check if order number exists
		int index = orders.indexOf(new ProductOrder(orderNumber,null,null,""));
		if (index == -1)
		{
			//throwns an exception if ordernumber is not found
			errMsg = "Order " + orderNumber + " Not Found";
			InvalidOrderNumberException exception = new InvalidOrderNumberException("Order " + orderNumber + " Not Found");
			throw exception;
		}
		ProductOrder order = orders.get(index);
		orders.remove(index);
	}

	// Sort products by increasing price
	public void sortByPrice()
	{
		Collections.sort(products, new PriceComparator());
	}

	private class PriceComparator implements Comparator<Product>
	{
		public int compare(Product a, Product b)
		{
			if (a.getPrice() > b.getPrice()) return 1;
			if (a.getPrice() < b.getPrice()) return -1;	
			return 0;
		}
	}

	// Sort products alphabetically by product name
	public void sortByName()
	{
		Collections.sort(products, new NameComparator());
	}

	private class NameComparator implements Comparator<Product>
	{
		public int compare(Product a, Product b)
		{
			return a.getName().compareTo(b.getName());
		}
	}

	// Sort products alphabetically by product name
	public void sortCustomersByName()
    {
      //Sort customers alphabetically using collections. The comparator decides which characteristics should be sorted.
  	  Collections.sort(customers, new CompareCustomerName());
    }

	public class CompareCustomerName implements Comparator<Customer> 
	{

		public int compare(Customer o1, Customer o2) {
		  //compares the name of two customers
		  return o1.getName().compareTo(o2.getName());
  
		}
  
	  }


}

//exception classes  
class UnknownCustomerException extends RuntimeException
{	
	public UnknownCustomerException(){}

	public UnknownCustomerException(String message)
	{
		super(message);

	}
}

class UnknownProductException extends RuntimeException
{	
	public UnknownProductException(){}

	public UnknownProductException(String message)
	{
		super(message);

	}
}

class InvalidProductOptionsException extends RuntimeException
{	
	public InvalidProductOptionsException(){}

	public InvalidProductOptionsException(String message)
	{
		super(message);

	}
}

class ProductOutOfStockException extends RuntimeException
{	
	public ProductOutOfStockException(){}

	public ProductOutOfStockException(String message)
	{
		super(message);

	}
}

class InvalidCustomerNameException extends RuntimeException
{	
	public InvalidCustomerNameException(){}

	public InvalidCustomerNameException(String message)
	{
		super(message);

	}
}

class InvalidCustomerAddressException extends RuntimeException
{	
	public InvalidCustomerAddressException(){}

	public InvalidCustomerAddressException(String message)
	{
		super(message);

	}
}

class InvalidOrderNumberException extends RuntimeException
{	
	public InvalidOrderNumberException(){}

	public InvalidOrderNumberException(String message)
	{
		super(message);
	}
}



