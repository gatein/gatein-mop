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
package org.gatein.mop.core.api.composer;

import org.gatein.mop.core.api.AbstractPOMTestCase;
import org.gatein.mop.core.api.ModelImpl;
import org.gatein.mop.api.workspace.Site;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.Page;
import org.gatein.mop.api.workspace.ui.UIContainer;
import org.gatein.mop.api.workspace.ui.UIComponent;
import org.gatein.mop.api.composer.ComponentIterator;
import org.gatein.mop.api.composer.IterationType;
import org.gatein.mop.api.composer.PageIterator;
import org.gatein.mop.api.composer.StructureIterator;
import org.gatein.mop.api.composer.PageIteratorStrategies;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class StructureIteratorTestCase extends AbstractPOMTestCase {

  public void testCompoundPage() {
    ModelImpl model = pomService.getModel();
    Site site = model.getWorkspace().addSite(ObjectType.PORTAL_SITE, "site");
    Page root = site.getRootPage();

    //
    Page t = root.addChild("t");
    UIContainer t0 = t.getRootComponent();
    UIComponent t1 = t0.addChild(ObjectType.WINDOW, "t1");
    UIComponent t2 = t0.addChild(ObjectType.BODY, "t2");
    UIComponent t3 = t0.addChild(ObjectType.WINDOW, "t3");

    //
    Page u = root.addChild("u");
    UIContainer u0 = u.getRootComponent();
    UIComponent u1 = u0.addChild(ObjectType.WINDOW, "u1");
    UIComponent u2 = u0.addChild(ObjectType.BODY, "u2");

    //
    Page a = root.addChild("a");
    a.setTemplate(t);
    UIContainer a0 = a.getRootComponent();
    UIComponent a1 = a0.addChild(ObjectType.WINDOW, "a1");
    UIComponent a2 = a0.addChild(ObjectType.WINDOW, "a2");
    PageIterator ia = new PageIterator(a, PageIteratorStrategies.PAGE_TEMPLATE);
    assertStart(ia, t0);
    assertStart(ia, t1);
    assertEnd(ia, t1);
    assertStart(ia, a0);
    assertStart(ia, a1);
    assertEnd(ia, a1);
    assertStart(ia, a2);
    assertEnd(ia, a2);
    assertEnd(ia, a0);
    assertStart(ia, t3);
    assertEnd(ia, t3);
    assertEnd(ia, t0);
    assertDone(ia);

    //
    Page b = root.addChild("b");
    UIContainer b0 = b.getRootComponent();
    UIComponent b1 = b0.addChild(ObjectType.WINDOW, "b1");
    UIComponent b2 = b0.addChild(ObjectType.WINDOW, "b2");

    //
    Page c = root.addChild("c");
    c.setTemplate(u);


  }

  private void assertStart(StructureIterator i, UIComponent expectedComponent) {
    IterationType type = i.next();
    assertEquals(IterationType.START, type);
    UIComponent component = i.getComponent();
    assertSame(expectedComponent, component);
  }

  private void assertEnd(StructureIterator i, UIComponent expectedComponent) {
    IterationType type = i.next();
    assertEquals(IterationType.END, type);
    UIComponent component = i.getComponent();
    assertSame(expectedComponent, component);
  }

  private void assertDone(StructureIterator i) {
    IterationType type = i.next();
    assertEquals(IterationType.DONE, type);
    UIComponent component = i.getComponent();
    assertSame(null, component);
  }

  public void testSimplePage() {
    ModelImpl model = pomService.getModel();
    Site site = model.getWorkspace().addSite(ObjectType.PORTAL_SITE, "site");
    Page root = site.getRootPage();
    UIContainer c0 = root.getRootComponent();
    UIComponent c1 = c0.addChild(ObjectType.WINDOW, "c1");

    //
    PageIterator i = new PageIterator(root, PageIteratorStrategies.PAGE_TEMPLATE);
    assertStart(i, c0);
    assertStart(i, c1);
    assertEnd(i, c1);
    assertEnd(i, c0);
    assertDone(i);
  }

  public void testComponents() {
    ModelImpl model = pomService.getModel();
    Site site = model.getWorkspace().addSite(ObjectType.PORTAL_SITE, "site");
    Page root = site.getRootPage();
    UIContainer c0 = root.getRootComponent();
    UIComponent c1 = c0.addChild(ObjectType.WINDOW, "c1");
    UIContainer c2 = c0.addChild(ObjectType.CONTAINER, "c2");
    UIComponent c3 = c2.addChild(ObjectType.WINDOW, "c3");
    UIComponent c4 = c2.addChild(ObjectType.WINDOW, "c4");
    UIComponent c5 = c0.addChild(ObjectType.WINDOW, "c5");

    //
    ComponentIterator i0 = new ComponentIterator(c1);
    assertStart(i0, c1);
    assertEnd(i0, c1);
    assertDone(i0);

    //
    ComponentIterator i1 = new ComponentIterator(c0);
    assertStart(i1, c0);
    assertStart(i1, c1);
    assertEnd(i1, c1);
    assertStart(i1, c2);
    assertStart(i1, c3);
    assertEnd(i1, c3);
    assertStart(i1, c4);
    assertEnd(i1, c4);
    assertEnd(i1, c2);
    assertStart(i1, c5);
    assertEnd(i1, c5);
    assertEnd(i1, c0);
    assertDone(i1);
  }
}
