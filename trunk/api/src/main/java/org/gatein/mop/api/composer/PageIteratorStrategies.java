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
package org.gatein.mop.api.composer;

import org.gatein.mop.api.workspace.Page;

import java.util.Iterator;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public enum PageIteratorStrategies implements PageIteratorStrategy {

  SIMPLE {
    public Iterator<Page> iterator(Page page) {
      return Collections.singletonList(page).iterator();
    }},

  PAGE_TEMPLATE {
    public Iterator<Page> iterator(final Page page) {
      return new Iterator<Page>() {
        Page current = page;
        public boolean hasNext() {
          return current != null;
        }
        public Page next() {
          if (current == null) {
            throw new NoSuchElementException();
          }
          Page next = null;
          for (Page p = current;p != null;p = p.getParent()) {
            Page template = p.getTemplate();
            if (template != null) {
              next = template;
              break;
            }
          }
          Page tmp = current;
          current = next;
          return tmp;
        }
        public void remove() {
          throw new UnsupportedOperationException();
        }
      };
    }},

  PAGE_AND_SITE_TEMPLATE {
    public Iterator<Page> iterator(Page page) {
      throw new UnsupportedOperationException();
    }}
}
