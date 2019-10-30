package com.company.GameStore.dao;

import com.company.GameStore.dto.Console;

import java.util.List;

public interface ConsoleDao {

    Console getConsole(int consoleId);

    List<Console> getAllConsoles();

    List<Console> getConsoleByManufacturer(String manu);

    Console addConsole(Console console);

    void updateConsole(Console console);

    void deleteConsole(int consoleId);
}
