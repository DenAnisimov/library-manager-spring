package org.example.map;

import org.example.dto.AuthorDetailsDTO;
import org.example.entity.AuthorDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDetailsMapperTest {

    private AuthorDetailsMapper authorDetailsMapper;

    @BeforeEach
    void setUp() {
        authorDetailsMapper = Mappers.getMapper(AuthorDetailsMapper.class);
    }

    @Test
    void testMapToEntity() {
        AuthorDetailsDTO dto = new AuthorDetailsDTO();
        dto.setBriefBiography("Author Biography");
        dto.setLifeYears("1950");

        AuthorDetails entity = authorDetailsMapper.mapToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getBriefBiography(), entity.getBriefBiography());
        assertEquals(dto.getLifeYears(), entity.getLifeYears());
    }

    @Test
    void testMapToDTO() {
        AuthorDetails entity = new AuthorDetails();
        entity.setBriefBiography("Author Biography");
        entity.setLifeYears("1950");

        AuthorDetailsDTO dto = authorDetailsMapper.mapToDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getBriefBiography(), dto.getBriefBiography());
        assertEquals(entity.getLifeYears(), dto.getLifeYears());
    }

    @Test
    void testMapToDTOList() {
        AuthorDetails entity1 = new AuthorDetails();
        entity1.setBriefBiography("Author Biography");
        entity1.setLifeYears("1950");

        AuthorDetails entity2 = new AuthorDetails();
        entity2.setBriefBiography("Another Author Biography");
        entity2.setLifeYears("1950");

        List<AuthorDetails> entities = Arrays.asList(entity1, entity2);

        List<AuthorDetailsDTO> dtoList = authorDetailsMapper.mapToDTO(entities);

        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());

        AuthorDetailsDTO dto1 = dtoList.get(0);
        assertEquals(entity1.getId(), dto1.getId());
        assertEquals(entity1.getBriefBiography(), dto1.getBriefBiography());
        assertEquals(entity1.getLifeYears(), dto1.getLifeYears());

        AuthorDetailsDTO dto2 = dtoList.get(1);
        assertEquals(entity2.getId(), dto2.getId());
        assertEquals(entity2.getBriefBiography(), dto2.getBriefBiography());
        assertEquals(entity2.getLifeYears(), dto2.getLifeYears());
    }
}
