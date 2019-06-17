package com.proj.dao;

import com.proj.domain.Notation;
import javafx.collections.ObservableList;

public interface NotationDAO {

    Notation getNotationByID(long idNotation);

    boolean insertNotation(Notation notation);

    boolean updateNotation(Notation notation);

    boolean deleteNotation(long idNotation);

    ObservableList<Notation> getAllNotations();

    ObservableList<Notation> getNotationByVisited(String visited);

    Notation getFirstNotation();
}
