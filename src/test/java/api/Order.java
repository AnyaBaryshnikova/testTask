package api;

public class Order {

    private Integer id;
    private Integer petId;
    private Integer quantity;
    private String shipDate;
    private OrderStatus status;
    private boolean complete;

    public Order(Integer id, Integer petId, Integer quantity, String shipDate, OrderStatus status, boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPetId() {
        return petId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public boolean getComplete() {
        return complete;
    }
}
