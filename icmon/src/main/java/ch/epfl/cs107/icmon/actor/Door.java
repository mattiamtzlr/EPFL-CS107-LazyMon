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

    /**
     * Constructs a new Door (or rather a passage), which allows to pass from one area to another by walking "through" the
     * door.
     * @param destinationArea The <i>name</i> of the area to which the door <b>leads</b>. (String)
     * @param destinationCoords The position, where the player should enter the <b>new</b> area. (DiscreteCoordinates)
     * @param ownerArea The area, to which the door <b>belongs</b>. (Area)
     * @param mainCellCoords The position, where the door is located in its owner area. (DiscreteCoordinates)
     */
    public Door(String destinationArea, DiscreteCoordinates destinationCoords, Area ownerArea,
                DiscreteCoordinates mainCellCoords) {

        this(destinationArea, destinationCoords, ownerArea, mainCellCoords,
                new DiscreteCoordinates[0]);
    }

    /**
     * Constructs a new Door (or rather a passage), which allows to pass from one area to another by walking "through" the
     * door. The door can have multiple coordinates from which the player can walk through the door.
     * @param destinationArea The <i>name</i> of the area to which the door <b>leads</b>. (String)
     * @param destinationCoords The position, where the player should enter the <b>new</b> area. (DiscreteCoordinates)
     * @param ownerArea The area, to which the door <b>belongs</b>. (Area)
     * @param mainCellCoords The position, where the door is located in its owner area. (DiscreteCoordinates)
     * @param additionalCellCoords More positions, where the door is also accessible from in its owner area. (DiscreteCoordinates...)
     */
    public Door(String destinationArea, DiscreteCoordinates destinationCoords, Area ownerArea,
                DiscreteCoordinates mainCellCoords, DiscreteCoordinates... additionalCellCoords) {

        super(ownerArea, Orientation.UP, mainCellCoords);

        this.destinationArea = destinationArea;
        this.destinationCoords = destinationCoords;
        this.mainCellCoords = mainCellCoords;

        this.additionalCellCords = new ArrayList<>(Arrays.asList(additionalCellCoords));
    }

    /**
     * Returns the area, to which this door leads.
     * @return The <i>name</i> of the destination area. (String)
     */
    public String getDestinationArea() {
        return this.destinationArea;
    }

    /**
     * Returns the coordinates, at which the player should be placed, when passing through this door.
     * @return The main destination coordinates. (DiscreteCoordinates)
     */
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
