package com.company.GameStore.controller;

import com.company.GameStore.dto.TShirt;
import com.company.GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping( value = "/tshirt")
public class TShirtController {

    @Autowired
    ServiceLayer service;
//    TShirtDao tShirtDao;

    // ========= ADD TSHIRT =========

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public TShirt addShirt(@RequestBody @Valid TShirt shirt) {
        return service.addShirt(shirt);
    }

    // ========= GET ALL TSHIRTS =========

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<TShirt> getAllShirts () {
        if (service.getAllShirts().size() > 0) {
            return service.getAllShirts();
        } else {
            throw new IllegalArgumentException("No tshirts found");
        }
    }

    /*
    List<TShirt> getShirtByColor(String color);

    List<TShirt> getShirtBySize(String size);
     */

    // ========= GET TSHIRT BY COLOR =========

    @RequestMapping(value = "/GetColor/{color}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<TShirt> getShirtByColor(@PathVariable String color) {
        if (service.getShirtByColor(color).size() > 0) {
            return service.getShirtByColor(color);
        } else {
            throw new IllegalArgumentException("No tshirts found in that color");
        }
    }

    // ========= GET TSHIRT BY SIZE =========

    @RequestMapping(value = "/GetSize/{size}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<TShirt> getShirtBySize(@PathVariable String size) {
        if (service.getShirtBySize(size).size() > 0) {
            return service.getShirtBySize(size);
        } else {
            throw new IllegalArgumentException("No tshirts found in that size");
        }
    }

    // ========= GET TSHIRT =========

    @RequestMapping(value = "/{tshirtId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public TShirt getShirt(@PathVariable int tshirtId) {
//        if (service.getAllShirts().contains(service.getShirt(tshirtId))) {
            return service.getShirt(tshirtId);
//        } else {
//            throw new IllegalArgumentException("No tshirt with matching ID");
//        }
    }

    // ========= UPDATE TSHIRT =========

    @RequestMapping(value = "/{tshirtId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateShirt(@RequestBody @Valid TShirt shirt, @PathVariable int tshirtId) {
//        if (service.getAllShirts().contains(service.getShirt(tshirtId))) {
            shirt.setTshirtId(tshirtId);
            service.updateShirt(shirt);
//        } else {
//            throw new IllegalArgumentException("No tshirt with matching ID to update");
//        }
    }

    // ========= DELETE TSHIRT =========

    @RequestMapping(value = "/{tshirtId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteShirt(@PathVariable int tshirtId) {
//        if (service.getAllShirts().contains(service.getShirt(tshirtId))) {
            service.deleteShirt(tshirtId);
//        }
    }

}
