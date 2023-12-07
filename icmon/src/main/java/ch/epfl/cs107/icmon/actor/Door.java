package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class Door extends AreaEntity {
    private final String destinationArea;
    private final DiscreteCoordinates destinationCoords;
    private final DiscreteCoordinates mainCellCoords;
    private final ArrayList<DiscreteCoordinates> additionalCellCords;

    public Door(String destinationArea, DiscreteCoordinates destinationCoords, Area ownerArea,
                DiscreteCoordinates mainCellCoords) {

        this(destinationArea, destinationCoords, ownerArea, mainCellCoords,
                new DiscreteCoordinates[0]);
    }

    public Door(String destinationArea, DiscreteCoordinates destinationCoords, Area ownerArea,
                DiscreteCoordinates mainCellCoords, DiscreteCoordinates... additionalCellCoords) {

        super(ownerArea, Orientation.UP, mainCellCoords);

        this.destinationArea = destinationArea;
        this.destinationCoords = destinationCoords;
        this.mainCellCoords = mainCellCoords;

        this.additionalCellCords = new ArrayList<>(Arrays.asList(additionalCellCoords));
    }

    public String getDestinationArea() {
        return this.destinationArea;
    }

    public DiscreteCoordinates getDestinationCoords() {
        return this.destinationCoords;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        this.additionalCellCords.add(this.mainCellCoords);
        return this.additionalCellCords;
    }

    @Override
    public void draw(Canvas canvas) {}

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }
}
