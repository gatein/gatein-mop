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

import org.gatein.mop.api.workspace.ui.UIContainer;
import org.gatein.mop.api.workspace.link.PageLink;
import org.gatein.mop.api.Attributes;

import java.util.Collection;

/**
 * <p>A page is a pointer with useful information pointing to a component structure.</p>
 *
 * <p>Page can be organized as hierarchies  used for the single purpose of performing
 * property inheritance. The pages of a same hierarchy belong to the same site.</p>
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public interface Page extends TemplatizedObject, WorkspaceCustomizationContext {

  /**
   * Returns the page name.
   *
   * @return the page name
   */
  String getName();

  /**
   * Returns the page type.
   *
   * @return the page type
   */
  ObjectType<? extends Page> getObjectType();

  /**
   * Returns the page attributes.
   *
   * @return the attributes
   */
  Attributes getCascadingAttributes();

  /**
   * Returns the site that owns the page.
   *
   * @return the owner site
   */
  Site getSite();
  
  /**
   * Returns the parent page
   *
   * @return the parent page
   */
  Page getParent();

  /**
   * Returns the children.
   *
   * @return the children
   */
  Collection<? extends Page> getChildren();

  /**
   * Returns a named child or null if it does not exist.
   *
   * @param name the child name
   * @return a child
   */
  Page getChild(String name);

  /**
   * Create a child page and returns it.
   *
   * @param name the child name
   * @return the child page
   * @throws NullPointerException if the name is null
   * @throws IllegalArgumentException if a child with such name already exists
   */
  Page addChild(String name) throws NullPointerException, IllegalArgumentException;

  /**
   * Returns the layout of the page. A layout is automatically when the page is created and is bound to the page
   * life cycle.
   *
   * @return the page layout.
   */
  UIContainer getRootComponent();

  /**
   * Returns the set of navigations pointing to this page.
   *
   * @return the related navigations
   */
  Collection<PageLink> getNavigations();

  /**
   * Destroys the page.
   */
  void destroy();

  /**
   * Returns the templatized objects for this page.
   *
   * @param templatizedType the type of templatized
   * @param <T> the templatized workspace object type parameter
   * @return the collection of templatized objects
   */
  <T extends TemplatizedObject> Collection<? extends T> getTemplatizedObjects(ObjectType<T> templatizedType);

}
