package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }


    public enum DAOTypes {
        CUSTOMER,ITEM,CATEGORY,PAYMENT,EMPLOYEE,SUPPLIER,ORDERDETAILS,ORDER,RETURN,USER
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case CATEGORY:
                return new CategoryDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case ORDERDETAILS:
                return new OrderDetailsDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case RETURN:
                return new ReturnDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }
}
