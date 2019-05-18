import com.google.gson.Gson;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        port(8080);

        final CarService carService = new CarServiceMapImpl();

        post("/cars", (request, response) -> {
            response.type("application/json");

            Car car = new Gson().fromJson(request.body(), Car.class);
            carService.addCar(car);

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        get("/cars", (request, response) -> {
            response.type("application/json");

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(carService.getCars())));
        });

        get("/cars/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(carService.getCar(request.params(":id")))));
        });

        put("/cars/:id", (request, response) -> {
            response.type("application/json");

                Car toEdit = new Gson().fromJson(request.body(), Car.class);
            Car editedUser = carService.editCar(toEdit);

            if (editedUser != null) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(editedUser)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJson("User not found or error in edit")));
            }
        });

        delete("/cars/:id", (request, response) -> {
            response.type("application/json");

            carService.deleteCar(request.params(":id"));
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "user deleted"));
        });

        options("/cars/:id", (request, response) -> {
            response.type("application/json");

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, (carService.carExists(request.params(":id"))) ? "User exists" : "User does not exists"));
        });

    }
}
