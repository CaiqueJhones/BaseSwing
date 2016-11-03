/*
 * AbstractDaoLiteTest.java.java
 *
 * Copyright 2016 Caique Jhones
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.jhonesdeveloper.swing.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

/**
 * @author Caique Jhones
 */
public class AbstractDaoLiteTest {
	
	private static ConnectionSource connectionSource;
	private static AbstractDaoLite<EntityMock, Long> dao;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		connectionSource = ConnectionBuilder.connectionSource(EntityMock.class);
		dao = new DaoLite(connectionSource, EntityMock.class);
	}
	
	@AfterClass
	public static void finishClass() throws Exception {
		if (connectionSource != null) {
			connectionSource.close();
		}
	}
	
	@After
	public void finish() throws Exception {
		TableUtils.clearTable(connectionSource, EntityMock.class);
	}
	
	@Test
	public void testSave() throws Exception {
		EntityMock entity = EntityMockBuilder.getDefault();
		int save = dao.save(entity);
		assertEquals(1, save);
		assertNotNull(entity.id);
		assertEquals(1, dao.count());
	}
	
	@Test
	public void testSave2() throws Exception {
		EntityMock a = new EntityMockBuilder().withName("Fabian").withAge(20).build();
		EntityMock b = new EntityMockBuilder().withName("Lucian").withAge(22).build();
		
		int save = dao.save(Arrays.asList(a, b));
		
		assertEquals(2, save);
		assertNotNull(a.getId());
		assertNotNull(b.getId());
	}
	
	@Test
	public void testDelete() throws Exception {
		EntityMock a = new EntityMockBuilder().withName("Fabian").withAge(20).build();
		dao.save(a);
		assertNotNull(a.getId());
		
		int delete = dao.delete(a.getId());
		assertEquals(1, delete);
		assertEquals(0, dao.count());
	}
	
	@Test
	public void testDelete2() throws Exception {
		EntityMock a = new EntityMockBuilder().withName("Fabian").withAge(20).build();
		EntityMock b = new EntityMockBuilder().withName("Lucian").withAge(22).build();
		dao.save(Arrays.asList(a, b));
		
		assertEquals(2, dao.count());
		
		int delete = dao.delete(Arrays.asList(a.id, b.id));
		assertEquals(2, delete);
		assertEquals(0, dao.count());
	}
	
	@Test
	public void testDeleteAll() throws Exception {
		testSave2();
		assertEquals(2, dao.count());
		
		dao.deleteAll();
		assertEquals(0, dao.count());
	}
	
	@Test
	public void testFindById() throws Exception {
		EntityMock a = EntityMockBuilder.getDefault();
		dao.save(a);
		
		EntityMock byId = dao.findById(a.getId());
		assertEquals(a, byId);
	}
	
	@Test
	public void testFindByParameter() throws Exception {
		EntityMock a = EntityMockBuilder.getDefault();
		dao.save(a);
		
		List<EntityMock> byParameter = dao.findByParameter("name", a.getName());
		assertEquals(1, byParameter.size());
		assertEquals(a, byParameter.get(0));
	}
	
	@Test
	public void testFindAllId() throws Exception {
		EntityMockBuilder builder = new EntityMockBuilder();
		EntityMock a = builder.withName("Fabian").withAge(20).build();
		EntityMock b = builder.withName("Lucian").withAge(22).build();
		dao.save(Arrays.asList(a, b));
		
		List<EntityMock> all = dao.findAll(Arrays.asList(a.getId(), b.getId()));
		assertEquals(2, all.size());
	}
	
	@Test
	public void testFindAll() throws Exception {
		EntityMock a = new EntityMockBuilder().withName("Fabian").withAge(20).build();
		EntityMock b = new EntityMockBuilder().withName("Lucian").withAge(22).build();
		dao.save(Arrays.asList(a, b));
		
		List<EntityMock> all = dao.findAll();
		assertEquals(2, all.size());
	}
	
	private static class DaoLite extends AbstractDaoLite<EntityMock, Long> {
		
		public DaoLite(ConnectionSource connectionSource, Class<EntityMock> clazz) throws RuntimeException {
			super(connectionSource, clazz);
		}
		
	}
	
	@DatabaseTable(tableName = "persons")
	private static class EntityMock {
		
		@DatabaseField(generatedId = true)
		private Long id;
		@DatabaseField(canBeNull = false)
		private String name;
		@DatabaseField(canBeNull = false)
		private int age;
		
		public EntityMock() {
		}
		
		public EntityMock(Long id, String name, int age) {
			this.id = id;
			this.name = name;
			this.age = age;
		}
		
		public Long getId() {
			return id;
		}
		
		public void setId(Long id) {
			this.id = id;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public int getAge() {
			return age;
		}
		
		public void setAge(int age) {
			this.age = age;
		}
		
		@Override
		public boolean equals(final Object other) {
			if (!(other instanceof EntityMock)) {
				return false;
			}
			EntityMock castOther = (EntityMock) other;
			return Objects.equals(id, castOther.id) && Objects.equals(name, castOther.name)
					&& Objects.equals(age, castOther.age);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(id, name, age);
		}
		
		@Override
		public String toString() {
			return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id", id).append("name", name)
					.append("age", age).toString();
		}
		
	}
	
	public static class EntityMockBuilder {
		private EntityMock mock;
		
		public EntityMockBuilder() {
			mock = new EntityMock();
		}
		
		public EntityMockBuilder withId(long id) {
			mock.setId(id);
			return this;
		}
		
		public EntityMockBuilder withName(String name) {
			mock.setName(name);
			return this;
		}
		
		public EntityMockBuilder withAge(int age) {
			mock.setAge(age);
			return this;
		}
		
		public EntityMock build() {
			return mock;
		}
		
		public static EntityMock getDefault() {
			return new EntityMock(null, "Marck", 32);
		}
	}
}
