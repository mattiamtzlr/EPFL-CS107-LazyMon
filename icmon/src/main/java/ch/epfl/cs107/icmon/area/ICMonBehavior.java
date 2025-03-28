package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.AreaBehavior;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.window.Window;

public class ICMonBehavior extends AreaBehavior {
    /**
     * ICMonBehavior Constructor
     *
     * @param window The current window. (Window)
     * @param name Name of the corresponding behavior. (String)
     */
    public ICMonBehavior(Window window, String name) {
        super(window, name);
        int height = getHeight();
        int width = getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ICMonCellType color = ICMonCellType.toType(getRGB(height - 1 - y, x));
                setCell(x, y, new ICMonCell(x, y, color));
            }
        }
    }

    /**
     * Defines all possible walking types.
     */
    public enum AllowedWalkingType {
        NONE,
        SURF,
        FEET,
        ALL // all of the above
    }

    /**
     * All possible cell types. The <code>type</code> attribute is a signed 32-bit integer, which corresponds to an RGBA color.
     */
    public enum ICMonCellType {
        NULL(0, AllowedWalkingType.NONE),
        WALL(-16777216, AllowedWalkingType.NONE),
        BUILDING(-8750470, AllowedWalkingType.NONE),
        INTERACT(-256, AllowedWalkingType.NONE),
        DOOR(-195580, AllowedWalkingType.ALL),
        INDOOR_WALKABLE(-1, AllowedWalkingType.FEET),
        OUTDOOR_WALKABLE(-14112955, AllowedWalkingType.FEET),
        WATER(-16776961, AllowedWalkingType.SURF),
        GRASS(-16743680, AllowedWalkingType.FEET);

        private final int type;
        private final AllowedWalkingType walkingType;

        ICMonCellType(int type, AllowedWalkingType walkingType) {
            this.type = type;
            this.walkingType = walkingType;
        }

        public AllowedWalkingType getWalkingType() {
            return walkingType;
        }

        public static ICMonCellType toType(int type) {
            for (ICMonCellType ict : ICMonCellType.values()) {
                if (ict.type == type)
                    return ict;
            }
            // When you add a new type, you can print the int value here before assign it to a type
            System.out.println(type);
            return NULL;
        }
    }

    /**
     * Cell class specific to this game.
     */
    public class ICMonCell extends Cell{
        private final ICMonCellType type;

        /**
         * ICMonCell Constructor
         * @param x    (int): x coordinate of the cell
         * @param y    (int): y coordinate of the cell
         * @param type (ICCellType), not null
         */
        public ICMonCell(int x, int y, ICMonCellType type) {
            super(x, y);
            this.type = type;
        }

        public ICMonCellType getType() {
            return type;
        }

        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }

        @Override
        protected boolean canEnter(Interactable entity) {
            if (this.getType().getWalkingType() == AllowedWalkingType.NONE)
                return false;

            if (entity instanceof ICMonPlayer && this.getType().getWalkingType() == AllowedWalkingType.SURF)
                return ((ICMonPlayer) entity).hasSurf();

            if (!entity.takeCellSpace())
                return true;

            for (Interactable entityInCell : entities)
                if (entityInCell.takeCellSpace())
                    return false;
            return true;
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
}
