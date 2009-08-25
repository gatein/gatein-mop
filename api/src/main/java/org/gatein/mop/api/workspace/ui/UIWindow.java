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
package org.gatein.mop.api.workspace.ui;

import org.gatein.mop.api.content.CustomizationContext;
import org.gatein.mop.api.content.Customization;
import org.gatein.mop.api.content.ContentType;

/**
 * The window is user interface component that points to a content.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public interface UIWindow extends UIComponent, CustomizationContext {

  /**
   * Returns the customization of this window or null if no customization exists.
   *
   * @return the window customization
   */
  Customization<?> getCustomization();

  /**
   * Customizes the specified content.
   *
   * @param contentType the content type
   * @param contentId the content id
   * @param state the content state
   * @param <S> the content type parameter
   * @return the created customization
   */
  <S> Customization<S> customize(ContentType<S> contentType, String contentId, S state);

  /**
   * Specialize the specified customization.
   *
   * @param customization the customization to specialise
   * @param <S> the content type parameter
   * @return the created customization
   */
  <S> Customization<S> customize(Customization<S> customization);

}
