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

/**
 * Provides access to content as seen by the framework.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public interface ContentManager {

  /**
   * Returns the representation of a specified content or null if it cannot be found.
   *
   * @param contentType the content type
   * @param contentId the content id
   * @return the content
   * @param <S> the state type
   */
  <S> Content<S> getContent(ContentType<S> contentType, String contentId);

  /**
   * Returns the representation of a specified content or null if it cannot be found.
   *
   * @param mimeType the mime type
   * @param contentId the content id
   * @return the content
   */
  Content<?> getContent(String mimeType, String contentId);

}
