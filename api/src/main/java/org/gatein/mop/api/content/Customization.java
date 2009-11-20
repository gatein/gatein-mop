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
package org.gatein.mop.api.content;

import java.util.Set;
import java.util.Collection;

/**
 * An entity representing the customization of a content.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 * @param <S> the content state type parameter
 */
public interface Customization<S> {

  /**
   * Returns the customization id
   *
   * @return the customization id
   */
  String getId();

  /**
   * Returns the content this customization refers to.
   */
  Content<S> getContent();

  /**
   * Returns the content id this customization refers to.
   *
   * @return the content id
   */
  String getContentId();

  /**
   * Returns the content type this customization refers to.
   *
   * @return The content type
   */
  ContentType<S> getType();

  /**
   * Returns the related context of this customization.
   *
   * @return the related context
   */
  CustomizationContext getContext();

  /**
   * Returns the contexts that are associated with this customization. Note that the set returned
   * maintains the hierararchy order of the set from the most specific to the least specific.
   *
   * @return the contexts
   */
  Set<CustomizationContext> getContexts();

  /**
   * <p>Returns the stateful content associated with the specified customization contexts or null
   * if no customization can be created for the desired contexts. The returned customization
   * may not honour all provided the customization contexts.</p>
   *
   * <p>Calling the method with an empty set returns the default customization of the
   * content or null if none is available.</p>
   *
   * <p>Calling the method with a set of contexts that is not consistent will trigger an
   * <tt>IllegalArgumentException</tt> to be thrown. For instance two workspace contexts
   * specifying different pages cannot lead to determine a final context.</p>
   *
   * @param contexts the customization contexts
   * @return the content state
   */
  Customization<S> getCustomization(Set<CustomizationContext> contexts);

  /**
   * Customize the state with respect to the provided customization context.
   *
   * @param contexts the customization context
   * @return the customization
   */
  Customization<S> customize(Collection<CustomizationContext> contexts);

  /**
   * Returns the parent customization or null if this customization is full featured clone.
   *
   * @return the parent customization
   */
  Customization getParent();

  /**
   * Returns the customization virtual state that is computed from various state of the customization hierarchy.
   *
   * @return the virtual state
   */
  S getVirtualState();

  /**
   * Returns the customization state.
   *
   * @return the state
   */
  S getState();

  /**
   * Updates the customization state.
   *
   * @param state the customization state
   */
  void setState(S state);

  /**
   * Destroys the customization.
   */
  void destroy();

}
