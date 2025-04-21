package hse.studying.zoo2.presentation.controllers;

import hse.studying.zoo2.application.ports.in.AddEnclosureUseCase;
import hse.studying.zoo2.application.ports.in.CleanEnclosureUseCase;
import hse.studying.zoo2.application.ports.in.GetEnclosureUseCase;
import hse.studying.zoo2.application.ports.in.RemoveEnclosureUseCase;
import hse.studying.zoo2.domain.models.Enclosure;
import hse.studying.zoo2.domain.models.ids.EnclosureID;
import hse.studying.zoo2.domain.vo.Diet;
import hse.studying.zoo2.domain.vo.EnclosureType;
import hse.studying.zoo2.presentation.dto.request.EnclosureRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Enclosures", description = "Operations related to enclosures")
@RestController
@RequestMapping("/api/enclosures")
@RequiredArgsConstructor
public class EnclosureController {
    private final AddEnclosureUseCase addEnclosureUseCase;
    private final RemoveEnclosureUseCase removeEnclosureUseCase;
    private final GetEnclosureUseCase getEnclosureUseCase;
    private final CleanEnclosureUseCase cleanEnclosureUseCase;

    @Operation(summary = "Add an enclosure to the zoo")
    @PostMapping
    public ResponseEntity<Enclosure> addEnclosure(@Valid @RequestBody EnclosureRequest request) {
        EnclosureType type = new EnclosureType(
                request.typeName(),
                request.typeDiets().stream().map(Diet::valueOf).collect(Collectors.toSet())
        );
        var enclosure = addEnclosureUseCase.addEnclosure(type, request.area(), request.capacity());
        return ResponseEntity.status(HttpStatus.CREATED).body(enclosure);
    }

    @Operation(summary = "Remove an enclosure from the zoo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeEnclosure(@PathVariable UUID id) {
        removeEnclosureUseCase.removeEnclosure(new EnclosureID(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get an enclosure by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Enclosure> getEnclosureById(@PathVariable UUID id) {
        return ResponseEntity.ok(getEnclosureUseCase.getEnclosure(new EnclosureID(id)));
    }

    @Operation(summary = "Clean the enclosure")
    @PostMapping("/{id}/clean")
    public ResponseEntity<Void> clearEnclosure(@PathVariable UUID id) {
        cleanEnclosureUseCase.cleanEnclosure(new EnclosureID(id));
        return ResponseEntity.noContent().build();
    }
}
