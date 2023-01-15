import java.util.ArrayList;

public class Cart 
{
    private ArrayList<CartItem> carts;

    public Cart(ArrayList<CartItem> carts)
    {
        this.carts = carts;
    }

    public Cart()
    {
        this.carts = new ArrayList<CartItem>();
    }

    public ArrayList<CartItem> getCarts()
    {
        return carts;
    }

    public void setCarts(ArrayList<CartItem> carts)
    {
        this.carts = carts;
    }



}
