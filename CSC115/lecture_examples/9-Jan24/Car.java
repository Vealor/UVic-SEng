public class Car implements Drivable
{
	public Car()
	{
		make = "unknown";
		model = "unknown";
		color = "unknown";
	}

	public Car (String make, String model, String color)
	{
		this.make = make;
		this.model = model;
		this.color = color;
	}

	public void drive()
	{
		System.out.println("Driving.");
	}

	public void setModel (String newModel)
	{	
		model = newModel;
	}
	private String make;
	private String model;
	private String color;
}
