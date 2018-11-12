package pl.krasnowski.DigitalKitchen.model.events;

public enum SystemEventType {
    SystemEvent(SystemEvent.class),


    KitchenEvent(KitchenEvent.class),

    KitchenOrdersEvent(KitchenOrdersEvent.class),
    KitchenOrderYourAccepted(KitchenOrderYoursAccepted.class),

    ShoppingListItemEvent(ShoppingListItemEvent.class),
    NewShoppingListItem(NewShoppingListItem.class),
    ShoppingListItemChangedStatus(ShoppingListItemChangedStatus.class),

    ExpirationDateEvent(ExpirationDateEvent.class),
    ExpirationDateComesSoon(ExpirationDateComesSoon.class),
    ExpirationDateReached(ExpirationDateReached.class),


    MealEvent(MealEvent.class),
    MealTimeComesSoon(MealTimeComesSoon.class),
    MealTimeReached(MealTimeReached.class),

    MessageEvent(MessageEvent.class);

    private final Class eventType;

    SystemEventType(Class<?> eventType) {
        this.eventType = eventType;
    }

    public Class getEventType() {
        return eventType;
    }
}
