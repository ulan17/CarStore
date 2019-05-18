import java.util.Collection;
import java.util.HashMap;

public class CarServiceMapImpl implements CarService {

    private HashMap<String, Car> carMap;

    CarServiceMapImpl() {
        carMap = new HashMap<>();
    }

    @Override
    public void addCar(Car car) {
        carMap.put(car.getId(), car);
    }

    @Override
    public Collection<Car> getCars() {
        return carMap.values();
    }

    @Override
    public Car getCar(String id) {
        return carMap.get(id);
    }

    @Override
    public Car editCar(Car car) throws CarException {
        try {
            Car toEdit = carMap.get(car.getId());

            if (toEdit == null)
                throw new CarException("User not found");

            if (car.getTitle() != null) toEdit.setTitle(car.getTitle());

            if (car.getDescription() != null) toEdit.setDescription(car.getDescription());

            if (car.getModel() != null) toEdit.setModel(car.getModel());

            if (car.getCity() != null) toEdit.setCity(car.getCity());

            if (car.getPrice() != null) toEdit.setPrice(car.getPrice());

            if (car.getNumber() != null) toEdit.setNumber(car.getNumber());

            return toEdit;

        } catch (Exception ex) {
            throw new CarException(ex.getMessage());
        }
    }

    @Override
    public void deleteCar(String id) {
        carMap.remove(id);
    }

    @Override
    public boolean carExists(String id) {
        return carMap.containsKey(id);
    }
}
