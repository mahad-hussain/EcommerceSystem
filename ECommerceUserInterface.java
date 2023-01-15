import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.EOFException;
import javax.swing.JOptionPane;

// Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface
{
	public static void main(String[] args)
	{
		// Create the system
		ECommerceSystem amazon = new ECommerceSystem();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		
		// Process keyboard actions

		try{
			
			while (scanner.hasNextLine())
			{
				
				String action = scanner.nextLine();

				if (action == null || action.equals("")) 
				{
					System.out.print("\n>");
					continue;
				}
				else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;

				else if (action.equalsIgnoreCase("PRODS"))	// List all products for sale
				{
					amazon.printAllProducts(); 
				}
				else if (action.equalsIgnoreCase("BOOKS"))	// List all books for sale
				{
					amazon.printAllBooks(); 
				}
				else if (action.equalsIgnoreCase("BOOKSBYAUTHOR"))	// ship an order to a customer
				{
					String author = "";

					System.out.print("Author: ");
					if (scanner.hasNextLine())
						author = scanner.nextLine();

					ArrayList<Book> books = amazon.booksByAuthor(author);
					Collections.sort(books);
					for (Book book: books)
						book.print();
				}
				else if (action.equalsIgnoreCase("CUSTS")) 	// List all registered customers
				{
					amazon.printCustomers();	
				}
				else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
				{
					amazon.printAllOrders();	
				}
				else if (action.equalsIgnoreCase("SHIPPED"))	// List all orders that have been shipped
				{
					amazon.printAllShippedOrders();	
				}
				else if (action.equalsIgnoreCase("NEWCUST"))	// Create a new registered customer
				{
					String name = "";
					String address = "";

					System.out.print("Name: ");
					if (scanner.hasNextLine())
						name = scanner.nextLine();

					System.out.print("\nAddress: ");
					if (scanner.hasNextLine())
						address = scanner.nextLine();

					amazon.createCustomer(name, address);

				}
				else if (action.equalsIgnoreCase("SHIP"))	// ship an order to a customer
				{
					String orderNumber = "";

					System.out.print("Order Number: ");
					if (scanner.hasNextLine())
						orderNumber = scanner.nextLine();

					ProductOrder order = amazon.shipOrder(orderNumber);
					if (order != null)
					{
						order.print();
					}
					else 
					{
						System.out.println(amazon.getErrorMessage());
					}
				}
				else if (action.equalsIgnoreCase("CUSTORDERS")) // List all the current orders and shipped orders for this customer
				{
					String customerId = "";

					System.out.print("Customer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					// Prints all current orders and all shipped orders for this customer
					amazon.printOrderHistory(customerId);

				}
				else if (action.equalsIgnoreCase("ORDER")) // order a product for a certain customer
				{
					String productId = "";
					String customerId = "";

					System.out.print("Product Id: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					String orderNumber = amazon.orderProduct(productId, customerId, "");
					if (orderNumber != null)
					{
						System.out.println("Order #" + orderNumber);
					}
					else
					{
						System.out.println(amazon.getErrorMessage());
					}
				}
				else if (action.equalsIgnoreCase("ORDERBOOK")) // order a book for a customer, provide a format (Paperback, Hardcover or EBook)
				{
					String productId = "";
					String customerId = "";
					String format = "";

					System.out.print("Product Id: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					System.out.print("\nFormat [Paperback Hardcover EBook]: ");
					if (scanner.hasNextLine())
						format = scanner.nextLine();

					String orderNumber = amazon.orderProduct(productId, customerId, format);
					if (orderNumber != null)
					{
						System.out.println("Order #" + orderNumber);
					}
					else
					{
						System.out.println(amazon.getErrorMessage());
					}
				}
				else if (action.equalsIgnoreCase("ORDERSHOES")) // order a book for a customer, provide a format (Paperback, Hardcover or EBook)
				{
					String productId = "";
					String customerId = "";
					String sizeColor = "";

					System.out.print("Product Id: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					System.out.print("\nSize (6, 7, 8, 9, 10) and Color (Black or Brown): ");
					if (scanner.hasNextLine())
						sizeColor = scanner.nextLine();

					String orderNumber = amazon.orderProduct(productId, customerId, sizeColor);
					if (orderNumber != null)
					{
						System.out.println("Order #" + orderNumber);
					}
					else
					{
						System.out.println(amazon.getErrorMessage());
					}
				}
				else if (action.equalsIgnoreCase("CANCEL")) // Cancel an existing order
				{
					String orderNumber = "";

					System.out.print("Order Number: ");
					if (scanner.hasNextLine())
						orderNumber = scanner.nextLine();

					amazon.cancelOrder(orderNumber);
				}
				else if (action.equalsIgnoreCase("SORTBYPRICE")) // sort products by price
				{
					amazon.sortByPrice();
				}
				else if (action.equalsIgnoreCase("SORTBYNAME")) // sort products by name (alphabetic)
				{
					amazon.sortByName();
				}
				else if (action.equalsIgnoreCase("SORTCUSTS")) // sort products by name (alphabetic)
				{
					amazon.sortCustomersByName();
				}

				else if (action.equalsIgnoreCase("ADDTOCART")) // adds a a product to the customer's cart
				{
					String productId = "";
					String customerId = "";
					String productOption = "";

					System.out.print("Product Id: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();


					amazon.addToCart(productId, customerId, productOption);
				}

				else if (action.equalsIgnoreCase("REMCARTITEM")) // removes a product from the customer's cart
				{
					String productId = "";
					String customerId = "";

					System.out.print("Product Id: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();


					amazon.remCartItem(productId, customerId);
				}

				else if (action.equalsIgnoreCase("PRINTCART")) // prints all the items in a customers cart 
				{
					String customerId = "";

					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();


					amazon.printCart();
				}

				else if (action.equalsIgnoreCase("ORDERITEMS")) //orders all the items in a customers cart and removes them from the cart
				{
					String customerId = "";

					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();


					amazon.orderItems(customerId);				
				}

				else if (action.equalsIgnoreCase("STATS")) //gets the number of times a product is ordered
				{
					String customerId = "";
					String productId = "";

					
					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					System.out.print("Product Id: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					amazon.stats(productId, customerId);


				}

				else if (action.equalsIgnoreCase("RATE")) // allows the user to rate products between 1 and 5 and the prints it 
				{
					String customerId = "";
					String productId = "";
					Integer  rating  = 0;
					String rate = "";

					
					System.out.print("\nCustomer Id: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					System.out.print("Product Id: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine();
	

					System.out.print("Rate: ");
					if (scanner.hasNextLine()){
						rate = scanner.nextLine();
						//if(rate>0 && rate< 5)
							//rating = rate;
					}
					amazon.rate(productId, customerId, rate);


				}

				System.out.print("\n>");


			}
		}

		//catches all the exceptions and prints error messages
		catch(UnknownCustomerException e1)
		{
			throw new UnknownCustomerException(amazon.errMsg);
		}

		catch(UnknownProductException e2)
		{
			throw new UnknownProductException(amazon.errMsg);		}

		catch(InvalidProductOptionsException e3)
		{
			throw new InvalidProductOptionsException(amazon.errMsg);

		}

		catch(ProductOutOfStockException e4)
		{
			throw new ProductOutOfStockException(amazon.errMsg);
		}

		catch( InvalidCustomerNameException e5)
		{
			throw new InvalidOrderNumberException(amazon.errMsg);

		}

		catch (InvalidCustomerAddressException e6)
		{
			throw new InvalidCustomerAddressException(amazon.errMsg);
		}

		catch(InvalidOrderNumberException e7)
		{
			throw new InvalidCustomerNameException(amazon.errMsg);

		}
	}
}

