public class CartItem 
{
    private Product product;
    private String productOptions;

    public CartItem(Product product, String productOptions)
    {
        this.product = product;
        this.productOptions = productOptions;
    }

    public CartItem(Product product)
    {
        this.product = product;
        this.productOptions = "";
    }

    public Product getProduct()
	{
		return product;
	}
	public void setProduct(Product product)
	{
		this.product = product;
	}

    public void print()
	{
		System.out.printf("\nProduct Id: %3s Product Name: %12s Options: %8s", product.getId(), product.getName(), productOptions);
	}

    public boolean equals(Object other)
	{
		Product otherP = (Product) other;
		return this.product.getId().equals(otherP.getId());
	}

}
