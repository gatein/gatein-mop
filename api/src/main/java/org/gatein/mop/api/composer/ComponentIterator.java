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

import org.gatein.mop.api.workspace.ui.UIComponent;
import org.gatein.mop.api.workspace.ui.UIContainer;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ComponentIterator implements StructureIterator {

  /** . */
  private final UIComponent root;

  /** . */
  private final LinkedList<ContainerVisit> stack;

  /** . */
  private UIComponent current;

  /** . */
  private IterationType type;

  public ComponentIterator(UIComponent root) {
    this.root = root;
    this.stack = new LinkedList<ContainerVisit>();
    this.type = null;
  }

  public UIComponent getComponent() {
    return current;
  }

  public IterationType next() {

    if (type == null) {
      current = root;
      type = IterationType.START;
    } else {
      if (type == IterationType.START) {
        if (current instanceof UIContainer) {
          UIContainer container = (UIContainer)current;
          Iterator<? extends UIComponent> iterator = container.getChildren().iterator();
          if (iterator.hasNext()) {
            stack.add(new ContainerVisit(container, iterator));
            current = iterator.next();
          } else {
            type = IterationType.END;
          }
        } else {
          type = IterationType.END;
        }
      } else {
        if (stack.size() > 0) {
          ContainerVisit visit = stack.getLast();
          if (visit.iterator.hasNext()) {
            type = IterationType.START;
            current = visit.iterator.next();
          } else {
            stack.removeLast();
            current = visit.container;
          }
        } else {
          current = null;
          return IterationType.DONE;
        }
      }
    }

    //
    return type;
  }

  private static class ContainerVisit {

    /** . */
    private final UIContainer container;

    /** . */
    private final Iterator<? extends UIComponent> iterator;

    private ContainerVisit(UIContainer container, Iterator<? extends UIComponent> iterator) {
      this.container = container;
      this.iterator = iterator;
    }
  }
}
