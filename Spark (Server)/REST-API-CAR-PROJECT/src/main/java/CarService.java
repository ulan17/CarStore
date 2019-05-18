import java.util.Collection;

public interface CarService {

    void addCar(Car car);

    Collection<Car> getCars();

    Car getCar(String id);

    Car editCar(Car car) throws CarException;

    void deleteCar(String id);

    boolean carExists(String id);

}
