package api;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Tests {
    private static final String URL = "https://petstore.swagger.io/v2/store/order";

    @Test
    public void getNotExistingOrderById() {
        Specification.installSpecification(Specification.requestSpecification(URL), Specification.responseSpecification(404));
        Map<String, String> resp = given()
                .when()
                .get(URL + "/0")
                .then()
                .log()
                .all()
                .extract()
                .body()
                .jsonPath().get(".");
        Assert.assertEquals("Сообщение об ошибке не соответсвует ожидаемому", "Order not found", resp.get("message"));

    }

    @Test
    public void getExistingOrderById() {
        Specification.installSpecification(Specification.requestSpecification(URL), Specification.responseSpecification(200));

        // подготовка тестовых данных
        int id = 3;
        int petId = 1;
        int quantity = 1;
        String shipDate = "2023-04-12T10:58:47.116+0000";
        Order order = new Order(id, petId, quantity, shipDate, OrderStatus.placed, true);

        given()
                .body(order)
                .when()
                .post(URL);

        Map<String, String> res = given()
                .when()
                .get(URL + "/" + id)
                .then()
                .log()
                .all()
                .extract()
                .body()
                .jsonPath().get(".");

        Assert.assertEquals("Дата не соответствует ожидаемой", shipDate, res.get("shipDate"));
        Assert.assertEquals("Статус не соответствует ожидаемому", OrderStatus.placed.name(), res.get("status"));
    }

    @Test
    public void postOrder() {
        Specification.installSpecification(Specification.requestSpecification(URL), Specification.responseSpecification(200));
        int id = 3;
        int petId = 1;
        int quantity = 1;
        String shipDate = "2023-04-12T10:58:47.116+0000";
        Order order = new Order(id, petId, quantity, shipDate, OrderStatus.placed, true);

        Map<String, String> resp = given()
                .body(order)
                .when()
                .post(URL)
                .then()
                .log()
                .all()
                .extract()
                .body()
                .jsonPath().get(".");

        Assert.assertNotNull("id заказа не присвоен", resp.get("id"));
        Assert.assertNotNull("petId заказа не присвоен", resp.get("petId"));
        Assert.assertNotNull("Количество не присвоено", resp.get("quantity"));
        Assert.assertNotNull("Дата не присвоена", resp.get("shipDate"));
        Assert.assertNotNull("Статус заказа не присвоен", resp.get("status"));
    }

    @Test
    public void postEmptyOrder() {
        Specification.installSpecification(Specification.requestSpecification(URL), Specification.responseSpecification(200));
        Order order = new Order(null, null, null, null, null, true);

        Map<String, String> resp = given()
                .body(order)
                .when()
                .post(URL)
                .then()
                .log()
                .all()
                .extract()
                .body()
                .jsonPath().get(".");

        Assert.assertNotNull("id заказа не присвоен", resp.get("id"));
        Assert.assertNotNull("petId заказа не присвоен", resp.get("petId"));
        Assert.assertNotNull("Количество не присвоено", resp.get("quantity"));
        Assert.assertNull("Дата присвоена", resp.get("shipDate"));
        Assert.assertNull("Статус заказа присвоен", resp.get("status"));
    }

}
