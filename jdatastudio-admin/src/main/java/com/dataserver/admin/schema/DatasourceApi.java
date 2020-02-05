package com.dataserver.admin.schema;

import com.dataserver.api.schema.JDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by gongxinyi on 2018/11/27.
 */
@Slf4j
@RestController
@RequestMapping("/_datasource")
public class DatasourceApi {

    @Autowired
    DataSourceService dataSourceService;

    @Autowired
    SchemaService schemaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addDataSource(@RequestBody final JDataSource jDataSource) {
        return ResponseEntity.ok(dataSourceService.save(jDataSource));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity editDataSource(@PathVariable("id") String id, @RequestBody JDataSource jDataSource) {
        jDataSource.setId(id);
        return ResponseEntity.ok(dataSourceService.save(jDataSource));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JDataSource> findDataSource(@PathVariable("id") String id) {
        return ResponseEntity.ok(dataSourceService.get(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<JDataSource>> list(@RequestParam final Map<String, Object> params) {
        List<JDataSource> jDataSources = dataSourceService.getAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", jDataSources.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(jDataSources);
    }

    @GetMapping("/sync/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity syncSchemas(@PathVariable("id") String id) {
        synchronized (this) {
            schemaService.syncSchemas(dataSourceService.get(id));
        }
        return ResponseEntity.ok().build();
    }
}
