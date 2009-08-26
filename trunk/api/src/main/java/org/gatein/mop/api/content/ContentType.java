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
 * Represents a content type.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 * @param <S> the content state type parameter
 */
public final class ContentType<S> {

  /** . */
  private final String mimeType;

  /** . */
  private final Class<S> stateClass;

  /**
   * Create a new content type.
   *
   * @param mimeType the mime type
   * @param stateClass the state class
   * @throws NullPointerException if any argument is null
   */
  public ContentType(String mimeType, Class<S> stateClass) throws NullPointerException {
    if (mimeType == null) {
      throw new NullPointerException("No null mime type accepted");
    }
    if (stateClass == null) {
      throw new NullPointerException("No null state class accepted");
    }

    //
    this.mimeType = mimeType;
    this.stateClass = stateClass;
  }

  public String getMimeType() {
    return mimeType;
  }

  public Class<S> getStateClass() {
    return stateClass;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof ContentType) {
      ContentType that = (ContentType)obj;
      return mimeType.equals(that.mimeType);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return mimeType.hashCode();
  }
}
