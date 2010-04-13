/**
 * Copyright (C) 2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.gatein.mop.api.workspace;

import org.gatein.mop.api.workspace.link.Link;

import java.util.List;

/**
 * <p>A navigation denotes a visual item that display a link to a referenced entity like a page. A navigation is
 * expressed as a tree where each navigation node owns an ordered list of named children.</p> <p/> <p>A navigation node
 * owns an optional link to a {@link org.gatein.mop.api.workspace.link.Link} that denotes the entity shown visually by
 * the navigation node.</p>
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public interface Navigation extends WorkspaceObject
{

   /**
    * Returns the navigation name.
    *
    * @return the navigation name
    */
   String getName();

   /**
    * Update the navigation name.
    *
    * @param name the new navigation name
    */
   void setName(String name);

   /**
    * Extends the object type to navigation.
    *
    * @return the object type
    */
   ObjectType<? extends Navigation> getObjectType();

   /**
    * Returns the site this navigation belongs to
    *
    * @return the site
    */
   Site getSite();

   /**
    * Return the navigation parent or null if it is a root.
    *
    * @return the navigation parent
    */
   Navigation getParent();

   /**
    * Returns the child navigations.
    *
    * @return the child navigations
    */
   List<Navigation> getChildren();

   /**
    * Returns a specified navigation.
    *
    * @param name the navigation name
    * @return a child navigation
    */
   Navigation getChild(String name);

   /**
    * Adds a child navigation that will be added to the last position among the ordered children list.
    *
    * @param name the child name
    * @return the child navigation
    * @throws NullPointerException when a null name is provided
    * @throws IllegalArgumentException when an illegal name is provided
    */
   Navigation addChild(String name) throws NullPointerException, IllegalArgumentException;

   /**
    * Destroys this navigation.
    */
   void destroy();

   /**
    * Returns the current link related to this navitation object.
    *
    * @return the link
    */
   Link getLink();

   /**
    * Link the navigation to a specifed type and returns the corresponding link subclass that
    * allows the configuration of the link.
    *
    * @param linkType the link type
    * @param <L> the link type parameter
    * @return the link object
    */
   <L extends Link> L linkTo(ObjectType<L> linkType);

   /**
    * Returns the templatized aspect of this navigation when it exists.
    *
    * @return the templatized
    */
   Templatized getTemplatized();
}
