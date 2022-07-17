package com.example.graphqldemo.controller;

import com.example.graphqldemo.doa.DepartmentDao;
import com.example.graphqldemo.domain.Department;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class DepartmentController {

  private final DepartmentDao departmentDao;

  private GraphQL graphQL;

  @Value("classpath:department.graphqls")
  private Resource schemaResource;

  public DepartmentController(DepartmentDao departmentDao) {
    this.departmentDao = departmentDao;
  }

  @PostConstruct
  public void loadSchema() throws IOException {
    File schemaFile = schemaResource.getFile();
    TypeDefinitionRegistry registry = new SchemaParser().parse(schemaFile);
    RuntimeWiring wiring = buildWiring();
    GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(registry, wiring);
    graphQL = GraphQL.newGraphQL(schema).build();
  }

  private RuntimeWiring buildWiring() {
    DataFetcher<List<Department>> fetcher1 =
        data -> {
          return departmentDao.findAll();
        };

    DataFetcher<Department> fetcher2 =
        data -> {
          return departmentDao.findByDepartmentId(data.getArgument("id"));
        };

    return RuntimeWiring.newRuntimeWiring()
        .type(
            "Query",
            typeWriting ->
                typeWriting
                    .dataFetcher("getAllDepartment", fetcher1)
                    .dataFetcher("findDepartmentId", fetcher2))
        .build();
  }

  @PostMapping("/get-all-departments")
  public ResponseEntity<Object> getAll(@RequestBody String query) {
    ExecutionResult result = graphQL.execute(query);
    return new ResponseEntity<Object>(result, HttpStatus.OK);
  }

  @PostMapping("/get-department-by-id")
  public ResponseEntity<Object> getPersonByEmail(@RequestBody String query) {
    ExecutionResult result = graphQL.execute(query);
    return new ResponseEntity<Object>(result, HttpStatus.OK);
  }
}
