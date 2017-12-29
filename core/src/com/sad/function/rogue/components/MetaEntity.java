package com.sad.function.rogue.components;

import com.sad.function.rogue.systems.EntityManager;

import java.util.List;
import java.util.UUID;

/**
 * Class used to create entities on the fly.
 *
 * The way this is composed allows us to add and remove components on the fly.
 *
 * Code is from here: https://github.com/adamgit/Entity-System-RDBMS-Beta--Java-/blob/master/EntitySystemJava/src/com/wikidot/entitysystems/rdbmsbeta/MetaEntity.java
 * The same goes for the code for the {@link EntityManager}
 */
public class MetaEntity {
    public static EntityManager defaultEntityManager;

    public static MetaEntity loadFromEntityManager( UUID e ) {
        MetaEntity metaEntity = new MetaEntity( e );

        return metaEntity;
    }

    /** Set to null as it has NOT been registered in any EntityManager yet. */
    public UUID entity = null;

    public EntityManager parentEntityManager;

    public String internalName;

    public MetaEntity() {
        if ( defaultEntityManager == null )
            throw new IllegalArgumentException( "There is no global EntityManager; create a new EntityManager before creating Entity's" );

        parentEntityManager = defaultEntityManager;

        entity = defaultEntityManager.createEntity();
    }

    public MetaEntity( UUID e ) {
        if (defaultEntityManager == null )
            throw new IllegalArgumentException( "There is no global EntityManager; create a new EntityManager before creating Entity's" );

        parentEntityManager = defaultEntityManager;

        entity = e;
    }

    /**
     * This is the main constructor for Entities - usually, you'll know which components you want them to have
     *
     * NB: this is a NON-lazy way of instantiating Entities - in low-mem situations, you may want to
     * use an alternative constructor that accepts the CLASS of each Component, rather than the OBJECT, and
     * which only instantiates / allocates the memory for the data of each component when that component is
     * (eventually) initialized.
     *
     * @param n the internal name that will be attached to this entity, and reported in debugging info
     * @param components
     */
    public MetaEntity( String n, Component... components )
    {
        this( components );

        internalName = n;
    }

    /**
     * This is the main constructor for Entities - usually, you'll know which components you want them to have
     *
     * NB: this is a NON-lazy way of instantiating Entities - in low-mem situations, you may want to
     * use an alternative constructor that accepts the CLASS of each Component, rather than the OBJECT, and
     * which only instantiates / allocates the memory for the data of each component when that component is
     * (eventually) initialized.
     *
     * @param components
     */
    public MetaEntity( Component... components )
    {
        this(); // takes care of getting the initial "entity" part

        for( Component c : components )
        {
            this.add( c );
        }
    }

    public void add ( Component c ) {
        parentEntityManager.addComponent(entity, c );
    }

    public <T extends Component> T get( Class<T> type ) {
        return parentEntityManager.getComponent( entity, type );
    }

    public <T extends Component> boolean has( Class<T> type ) {
        return null != get( type );
    }

    /**
     * Return a list of all components registered to this entity in the EntityManager.
     * @return
     */
    public List<? extends Component> getAll() {
        return parentEntityManager.getAllComponentsOnEntity( entity );
    }

    /**
     * Remove all components.
     */
    public void removeAll() {
        for ( Component c : getAll() ) {
            remove( c );
        }
    }

    /**
     * Remove a specific component from this entity.
     * @param c
     * @param <T>
     */
    public <T extends Component> void remove( Component c ) {
        parentEntityManager.removeComponent( entity, c );
    }
}