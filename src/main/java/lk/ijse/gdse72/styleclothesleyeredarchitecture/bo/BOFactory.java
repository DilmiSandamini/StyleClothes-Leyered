package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.Impl.*;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.PurchaseOrderBO;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        CUSTOMER,ITEM,CATEGORY,PAYMENT,EMPLOYEE,SUPPLIER,PURCHASE_ORDER,RETURN,USER
    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case CATEGORY:
                return new CategoryBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderBOImpl();
            case RETURN:
                return new ReturnBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}
