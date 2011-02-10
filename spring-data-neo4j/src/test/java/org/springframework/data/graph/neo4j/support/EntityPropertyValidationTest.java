package org.springframework.data.graph.neo4j.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.graph.neo4j.Friendship;
import org.springframework.data.graph.neo4j.Group;
import org.springframework.data.graph.neo4j.Person;
import org.springframework.data.graph.neo4j.finder.FinderFactory;
import org.springframework.data.graph.neo4j.support.node.Neo4jHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:org/springframework/data/graph/neo4j/support/Neo4jGraphPersistenceTest-context.xml"})
public class EntityPropertyValidationTest {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private GraphDatabaseContext graphDatabaseContext;

	@Before
	@Transactional
	public void cleanDb() {
		Neo4jHelper.cleanDb(graphDatabaseContext);
    }

    @Test(expected = ValidationException.class)
    @Transactional
    public void shouldFailValidationOnTooLongName() {
        new Person("Michael.........................", 35);
    }

    @Test(expected = ValidationException.class)
    @Transactional
    public void shouldFailValidationOnNegativeAge() {
        new Person("Michael", -1);
    }
    @Test(expected = ValidationException.class)
    @Transactional
    public void shouldFailValidationOnBigAge() {
        new Person("Michael", 110);
    }
}
