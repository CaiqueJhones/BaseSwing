/*
 * BasicDao.java.java
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

import java.util.List;

/**
 * Contém métodos básicos para busca, inserção, remoção e atualização em uma base de dados.
 * 
 * @author Caique Jhones
 * @version 1
 * @since 1.0.0
 */
public interface BasicDao<E, ID> {
	
	/**
	 * Este m�todo salva uma classe na tabela do banco. Caso a classe passada por par�metro j�
	 * exista, a tabela � atualizada com os novos valores, sen�o, ser� inserido.
	 * 
	 * @param clazz
	 *        Classe a ser salva.
	 * @return O n�mero de colunas afetadas. (Padr�o 1)
	 */
	int save(E entity);
	
	/**
	 * Cria caso n�o exista ou atualiza as entidades no banco.
	 * 
	 * @param entities
	 *        esntidades a serem salvas.
	 * @return o n�mero de linhas afetadas.
	 */
	int save(Iterable<E> entities);
	
	/**
	 * Remove da tabela de acordo com o ID passado por par�metro.
	 * 
	 * @param id
	 *        O id da classe a ser deletada.
	 * @return O n�mero de linhas afetadas.
	 */
	int delete(ID id);
	
	/**
	 * Remove v�rias linhas da tabela.
	 * 
	 * @param ids
	 *        chaves das linhas a serem removidas.
	 * @return n�mero de linhas afetadas.
	 */
	int delete(Iterable<ID> ids);
	
	/**
	 * Apaga todas as linhas da tabela.
	 * 
	 * @return o n�mero de linhas removidas.
	 */
	int deleteAll();
	
	/**
	 * Seleciona a linha pela chave prim�ria.
	 * 
	 * @param id
	 *        a chave da linha.
	 * @return a entidade recuperada do banco.
	 */
	E findById(ID id);
	
	/**
	 * seleciona linhas de acordo com o par�metro passado.
	 * 
	 * @param parameter
	 *        o nome do par�metro que ser� pesquisado.
	 * @param value
	 *        o valor do par�metro
	 * @return uma lista de entidades selecionadas.
	 */
	List<E> findByParameter(String parameter, Object value);
	
	/**
	 * Seleciona as linhas da tabela de acordo com suas chaves prim�rias passados por par�metro.
	 * 
	 * @param ids
	 *        chaves das linhas
	 * @return uma lista das entidades selecionadas.
	 */
	List<E> findAll(Iterable<ID> ids);
	
	/**
	 * Seleciona todas as linhas da tabela.
	 * 
	 * @return todas as linhas da tabela.
	 */
	List<E> findAll();
	
	/**
	 * Retorna o n�mero de linhas da tabela.
	 * 
	 * @return o n�mero de linhas da tabela.
	 */
	long count();
	
}
