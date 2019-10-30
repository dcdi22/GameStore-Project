package com.company.GameStore.controller;

import com.company.GameStore.dto.Console;
import com.company.GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/console")
public class ConsoleController {

    @Autowired
    ServiceLayer service;
//    ConsoleDao consoleDao;


    // =========== ADD CONSOLE ===========

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Console addConsole(@RequestBody @Valid Console console) {
        return service.addConsole(console);
    }

    // =========== GET ALL CONSOLES ===========

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Console> getAllConsoles() {
        if (service.getAllConsoles().size() > 0) {
            return service.getAllConsoles();
        } else {
            throw new IllegalArgumentException("No consoles found");
        }
    }

    // =========== GET CONSOLE BY MANUFACTURER ===========

    @RequestMapping(value = "/GetManufacturer/{manu}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Console> getConsoleByManufacturer(@PathVariable String manu) {
        if (service.getConsoleByManufacturer(manu).size() > 0) {
            return service.getConsoleByManufacturer(manu);
        } else {
            throw new IllegalArgumentException("No consoles by that manufacturer found.");
        }
    }

    // =========== GET CONSOLE ===========

    @RequestMapping(value = "/{consoleId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Console getConsole(@PathVariable int consoleId) {
//        if (service.getAllConsoles().contains(service.getConsole(consoleId))) {
            return service.getConsole(consoleId);
//        } else {
//            throw new IllegalArgumentException("No consoles with matching ID");
//        }
    }

    // =========== UPDATE CONSOLE ===========

    @RequestMapping(value = "/{consoleId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateConsole(@RequestBody @Valid Console console, @PathVariable int consoleId) {
//        if (service.getAllConsoles().contains(service.getConsole(consoleId))) {
            console.setConsoleId(consoleId);
            service.updateConsole(console);
//        } else {
//            throw new IllegalArgumentException("Can't update, no console with a matching ID");
//        }
    }

    // =========== DELETE CONSOLE ===========

    @RequestMapping( value = "/{consoleId}", method = RequestMethod.DELETE)
    @ResponseStatus( value = HttpStatus.OK)
    public void deleteConsole(@PathVariable int consoleId) {
//        if (service.getAllConsoles().contains(service.getConsole(consoleId))) {
            service.deleteConsole(consoleId);
//        } else {
//            throw new IllegalArgumentException("No matching ID, nothing to delete");
//        }
    }


}
