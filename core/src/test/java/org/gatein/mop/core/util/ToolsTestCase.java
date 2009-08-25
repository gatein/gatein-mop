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
package org.gatein.mop.core.util;

import junit.framework.TestCase;

import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ToolsTestCase extends TestCase {

  public void testIterators() {
    List<String> s1 = Arrays.asList();
    List<String> s2 = Arrays.asList("a");
    List<String> s3 = Arrays.asList("b", "c");
    Iterator<String> it = Tools.iterator(s1.iterator(), s2.iterator(), s3.iterator());
    assertEquals(Arrays.asList("a", "b", "c"), Tools.list(it));
  }
}
