package edu.xmu.test.hibernate.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import edu.xmu.test.hibernate.model.ClassRoom;
import edu.xmu.test.hibernate.model.Student;

public class OneToManyTest {
	private static final Logger logger = Logger.getLogger(OneToManyTest.class);
	PersonService personService;

	@Before
	public void setUp() {
		personService = new PersonService();
	}

	@Test
	public void nonCascadeSaveTest() {
		Student s1 = new Student();
		s1.setName("S_1");
		Student s2 = new Student();
		s2.setName("S_2");
		Student s3 = new Student();
		s3.setName("S_3");

		Set<Student> set = new HashSet<>();
		set.add(s1);
		set.add(s2);
		set.add(s3);

		ClassRoom classRoom = new ClassRoom();
		classRoom.setName("Class_A");

		classRoom.setStudents(set);
		// if we save classroom directly, the students in set are still
		// in transient state
		// We have to save student first. If not, TransientObjectException will
		// be thrown.
		// personService.save(s1); personService.save(s2);
		// personService.save(s3);
		// and since classroom_id column in student table is nullable by default
		// and it is
		// feasible we save student first, and then when we save classRoom,
		// classroom_id in student table
		// will be updated. 3 update sqls will be executed as hibernate need to
		// iterate all elements in classRoom and execute update one by one.
		personService.save(s1);
		personService.save(s2);
		personService.save(s3);
		personService.save(classRoom);
		// Therefore it is better to maintain the relationship on the many side
		// instead of the one side. And it is the drawback of many side
		// maintenance.
	}

	@Test
	public void cascadeSaveTest() {
	}

	@Test
	public void getTest() {
		ClassRoom classRoom = (ClassRoom) personService.get(ClassRoom.class, 1);
		// we have to set lazy="false" for <set> tag, if not, exception would be
		// thrown when we need to access students outside current session scope
		logger.info(classRoom);

	}

	@Test
	public void bidirectionalSaveTest() {
		ClassRoom classRoom = new ClassRoom();
		classRoom.setName("Class_A");
		personService.save(classRoom);
		
		Student s1 = new Student();
		s1.setName("S_1");
		s1.setClassRoom(classRoom);
		Student s2 = new Student();
		s2.setName("S_2");
		s2.setClassRoom(classRoom);
		Student s3 = new Student();
		s3.setName("S_3");
		s3.setClassRoom(classRoom);

		Set<Student> set = new HashSet<>();
		set.add(s1);
		set.add(s2);
		set.add(s3);

		classRoom.setStudents(set);

		// It doesn't work for one-to-many bidirectional mapping
		// because student's classroom refers to classroom's id
		// when we save s1, classroom is still not saved,
		// TransientObjectException will be thrown.
		// And it still doesn't work if we save classroom first because
		// s1,s2,s3 are still transient object and cannot be saved to student
		// table accordingly
		personService.save(s1);
		personService.save(s2);
		personService.save(s3);
	}

}
