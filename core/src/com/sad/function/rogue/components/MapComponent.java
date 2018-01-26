package com.sad.function.rogue.components;

import com.sad.function.rogue.objects.Dungeon;
import com.sad.function.rogue.systems.EntityManager;
import com.sad.function.rogue.visibility.RayCastVisibility;

import java.util.UUID;

public class MapComponent extends Component {
    private RayCastVisibility fovCalculator;
    private EntityManager em;

    public Dungeon dungeon;

    public MapComponent(EntityManager em) {
        this.em = em;
        fovCalculator = new RayCastVisibility();

        dungeon = new Dungeon();
    }

    public boolean isVisible(int x, int y) {
        UUID player = UUID.randomUUID();

        fovCalculator.Compute(dungeon,
                em.getComponent(player, TransformComponent.class).x,
                em.getComponent(player, TransformComponent.class).y,
                em.getComponent(player, LightSourceComponent.class).lightLevel
            );

        return fovCalculator.isVisible(x, y);
    }
}
