package com.sad.function.rogue.components;

import com.sad.function.rogue.dungeon.DungeonGenerator;
import com.sad.function.rogue.objects.Dungeon;
import com.sad.function.rogue.systems.EntityManager;
import com.sad.function.rogue.visibility.RayCastVisibility;

import java.util.UUID;

public class MapComponent extends Component {
    private RayCastVisibility fovCalculator;
    private EntityManager em;

    public Dungeon dungeon;
    public boolean fovRecompute = false;

    public MapComponent(EntityManager em) {
        this.em = em;
        fovCalculator = new RayCastVisibility();

        dungeon = new Dungeon();
    }

    public void generateDungeon() {
        UUID player = em.getAllEntitiesPossessingComponent(PlayerComponent.class).iterator().next();

        DungeonGenerator dg = new DungeonGenerator(
                dungeon,
                em
        );

        dg.makeMap();
    }
    /**
     * Recomputes the visibility grid if fovRecompute has been set.
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isVisible(int x, int y) {
        UUID player = em.getAllEntitiesPossessingComponent(PlayerComponent.class).iterator().next();

        if(fovRecompute) {
            fovCalculator.Compute(dungeon,
                    em.getComponent(player, TransformComponent.class).x,
                    em.getComponent(player, TransformComponent.class).y,
                    (int)em.getComponent(player, Light.class).distance
            );
        }

        return fovCalculator.isVisible(x, y);
    }
}
