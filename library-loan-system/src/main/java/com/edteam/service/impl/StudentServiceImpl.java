package com.edteam.service.impl;

import com.edteam.exception.BookNotFoundException;
import com.edteam.exception.DuplicateStudentException;
import com.edteam.exception.ErrorMessages;
import com.edteam.exception.StudentNotFoundException;
import com.edteam.model.Book;
import com.edteam.model.Student;
import com.edteam.service.IStudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentServiceImpl implements IStudentService {
    private List<Student> students = new ArrayList<>();

    @Override
    public void registerStudent(Student student)  {
        // Verificar si ya existe un estudiante con el mismo DNI y nombre
        boolean isDuplicateStudent = students.stream()
                .anyMatch(existingStudent -> existingStudent.getDni().equals(student.getDni())
                        && existingStudent.getName().equals(student.getName()));

        // Si hay un duplicado, lanzar excepción
        if (isDuplicateStudent) {
//            throw new DuplicateStudentException("Student with DNI " + student.getDni() +
//                    " and name " + student.getName() + " already exists");
            throw new DuplicateStudentException(
                    ErrorMessages.DUPLICATE_STUDENT.formatMessage(student.getDni(), student.getName())
            );
        }

        // Si no hay duplicados, agregar el estudiante
        students.add(student);
    }

    @Override
    public List<Student> listStudents() {
        return students;
    }

    @Override
    public Optional<Student> findStudentByDni(String dni) {
//        return students.stream()
//                .filter(student -> student.getDni().equals(dni))
//                .findFirst()
//                //.orElseThrow(() -> new StudentNotFoundException("Student with DNI " + dni + " not found"));
//                .orElseThrow(() -> new StudentNotFoundException(
//                        ErrorMessages.STUDENT_NOT_FOUND.formatMessage(dni)
//                ));
        Optional<Student> optionalStudent = students.stream()
                .filter(student -> student.getDni().equals(dni))
                .findFirst();

        if (optionalStudent.isEmpty()) {

            throw new StudentNotFoundException(
                       ErrorMessages.STUDENT_NOT_FOUND.formatMessage(dni));
        }

        return optionalStudent;
    }
}
