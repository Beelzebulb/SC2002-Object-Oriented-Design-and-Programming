package controller;

import entity.CentralManager;
import utils.Base;
/**
 * The BaseController class is the base class for all controllers in the system.
 * It extends the Base class and provides access to the CentralManager object.
 * @author Esther Teo Gek Wat
 * @version 1.0
 * @since 2023-04-15
 */

public class BaseController extends Base {
    /**
     * Object of class CentralManager
     */
    CentralManager centralManager;
    /**
     * Constructs a BaseController object with a CentralManager object.
     * @param centralManager the CentralManager object to be used in the controller
     */
    public BaseController(CentralManager centralManager) {
        super(centralManager);
        this.centralManager = centralManager;
    }
}
