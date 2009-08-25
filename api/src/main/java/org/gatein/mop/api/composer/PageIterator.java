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
import org.gatein.mop.api.workspace.ui.UIComponent;
import org.gatein.mop.api.workspace.ui.UIBody;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class PageIterator implements StructureIterator {

  /** . */
  private final ArrayList<PageVisit> list;

  /** . */
  private int index;

  /** . */
  private UIComponent current;

  public PageIterator(Page page, PageIteratorStrategy strategy) {
    ArrayList<PageVisit> list = new ArrayList<PageVisit>();
    Iterator<Page> iterator = strategy.iterator(page);
    while (iterator.hasNext()) {
      Page p = iterator.next();
      list.add(new PageVisit(p, new ComponentIterator(p.getRootComponent())));
    }

    //
    this.list = list;
    this.index = list.size() - 1;
    this.current = null;
  }

  public UIComponent getComponent() {
    return current;
  }

  public IterationType next() {
    while (index < list.size()) {
      PageVisit visit = list.get(index);
      switch (visit.iterator.next()) {
        case START: {
          UIComponent current = visit.iterator.getComponent();
          if (current instanceof UIBody) {
            index--;
            break;
          } else {
            this.current = current;
            return IterationType.START;
          }
        }
        case END: {
          UIComponent current = visit.iterator.getComponent();
          if (current instanceof UIBody) {
            break;
          } else {
            this.current = current;
            return IterationType.END;
          }
        }
        case DONE: {
          current = null;
          index++;
          break;
        }
        default:
          throw new AssertionError();
      }
    }

    //
    return IterationType.DONE;
  }

  private static class PageVisit {

    /** . */
    private final Page page;

    /** . */
    private final ComponentIterator iterator;

    private PageVisit(Page page, ComponentIterator iterator) {
      this.page = page;
      this.iterator = iterator;
    }
  }

}
