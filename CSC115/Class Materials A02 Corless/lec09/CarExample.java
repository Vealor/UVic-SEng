public class CarExample
{

	public static void main (String[] args)
	{
		Car c = new Car();
		Drivable d = new Car();

		c.drive();
		d.drive();
		c.setModel("bob");
	}
}


