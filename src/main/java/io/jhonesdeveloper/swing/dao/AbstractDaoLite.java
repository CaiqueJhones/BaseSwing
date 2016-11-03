/*
 * AbstractDaoLite.java.java
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Base para busca, inserção, remoção e atualização em uma base de dados utilizando o framework
 * ORMLite.
 * 
 * @author Caique Jhones
 * @version 1
 * @since 1.0.0
 * @see com.j256.ormlite.dao.Dao
 * @see com.j256.ormlite.dao.DaoManager
 * @param <E>
 *        Entidade que representa a tabela que sofrerá as mudanças.
 * @param <ID>
 *        Id associado a tabela.
 */
public abstract class AbstractDaoLite<E, ID> implements BasicDao<E, ID> {
	
	private static final Logger log = LoggerFactory.getLogger(AbstractDaoLite.class);
	protected Dao<E, ID> dao;
	
	public AbstractDaoLite(ConnectionSource connectionSource, Class<E> clazz) throws RuntimeException {
		try {
			this.dao = DaoManager.createDao(connectionSource, clazz);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public int save(E entity) throws RuntimeException {
		try {
			CreateOrUpdateStatus createOrUpdate = dao.createOrUpdate(entity);
			return createOrUpdate.getNumLinesChanged();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public int save(Iterable<E> entities) throws RuntimeException {
		int sum = 0;
		for (E e : entities) {
			sum += save(e);
		}
		return sum;
	}
	
	@Override
	public int delete(ID id) throws RuntimeException {
		try {
			int deleteById = dao.deleteById(id);
			return deleteById;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public int delete(Iterable<ID> ids) throws RuntimeException {
		try {
			List<ID> list = toList(ids);
			int deleteIds = dao.deleteIds(list);
			return deleteIds;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public int deleteAll() throws RuntimeException {
		try {
			int delete = dao.deleteBuilder().delete();
			return delete;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public E findById(ID id) throws RuntimeException {
		try {
			E entity = dao.queryForId(id);
			return entity;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<E> findByParameter(String parameter, Object value) throws RuntimeException {
		try {
			return dao.queryForEq(parameter, value);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<E> findAll(Iterable<ID> ids) throws RuntimeException {
		List<E> list = new ArrayList<>();
		for (ID id : ids) {
			list.add(findById(id));
		}
		return list;
	}
	
	@Override
	public List<E> findAll() throws RuntimeException {
		try {
			return dao.queryForAll();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public long count() throws RuntimeException {
		try {
			return dao.countOf();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	public Dao<E, ID> getDao() {
		return dao;
	}
	
	private List<ID> toList(Iterable<ID> ids) {
		List<ID> list = new ArrayList<>();
		ids.forEach(id -> list.add(id));
		return list;
	}
}
