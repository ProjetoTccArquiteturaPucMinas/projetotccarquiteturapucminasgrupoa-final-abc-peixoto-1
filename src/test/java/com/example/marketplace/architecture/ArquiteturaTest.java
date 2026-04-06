package com.example.marketplace.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

/**
 * Testes de arquitetura usando ArchUnit.
 * Valida que a arquitetura em camadas está sendo respeitada.
 */
class ArquiteturaTest {

    private static JavaClasses classesImportadas;

    @BeforeAll
    static void configurar() {
        classesImportadas = new ClassFileImporter()
                .importPackages("com.example.marketplace");
    }

    /**
     * Valida que as camadas seguem as regras de dependência:
     * - Controller pode acessar Service e Model
     * - Service pode acessar Repository e Model
     * - Repository pode acessar apenas Model
     * - Model não pode acessar nenhuma outra camada
     */
    @Test
    void validarArquitetura() {
        layeredArchitecture()
                .consideringOnlyDependenciesInLayers()

                // Definir camadas
                .layer("Controller").definedBy("..controller..")
                .layer("Service").definedBy("..service..")
                .layer("Repository").definedBy("..repository..")
                .layer("Model").definedBy("..model..")

                // Regras de dependência
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Controller").mayOnlyAccessLayers("Service", "Model")
                .whereLayer("Service").mayOnlyAccessLayers("Repository", "Model")
                .whereLayer("Repository").mayOnlyAccessLayers("Model")

                .check(classesImportadas);
    }

    /**
     * Valida que classes com "Controller" ou "Controlador" no nome estão no package
     * correto
     */
    @Test
    void controladoresDevemEstarNoPackageController() {
        ArchRule regra = classes()
                .that().haveSimpleNameContaining("Controller")
                .or().haveSimpleNameContaining("Controlador")
                .and().haveSimpleNameNotContaining("Test")
                .should().resideInAPackage("..controller..")
                .as("Controladores devem estar no package controller");

        regra.check(classesImportadas);
    }

    /**
     * Valida que classes com "Servico" no nome estão no package correto
     */
    @Test
    void servicosDevemEstarNoPackageService() {
        ArchRule regra = classes()
                .that().haveSimpleNameContaining("Servico")
                .and().haveSimpleNameNotStartingWith("Teste")
                .should().resideInAPackage("..service..")
                .as("Serviços devem estar no package service");

        regra.check(classesImportadas);
    }

    /**
     * Valida que classes com "Repository" ou "Repositorio" no nome estão no package
     * correto
     */
    @Test
    void repositoriosDevemEstarNoPackageRepository() {
        ArchRule regra = classes()
                .that().haveSimpleNameContaining("Repository")
                .or().haveSimpleNameContaining("Repositorio")
                .and().haveSimpleNameNotContaining("Test")
                .should().resideInAPackage("..repository..")
                .as("Repositórios devem estar no package repository");

        regra.check(classesImportadas);
    }

    /**
     * Valida que Controllers têm a anotação @Controller ou @RestController
     */
    @Test
    void controladoresDevemSerAnotadosCorretamente() {
        ArchRule regra = classes()
                .that().resideInAPackage("..controller..")
                .and()
                .haveSimpleNameContaining("Controller")
                .or().haveSimpleNameContaining("Controlador")
                .and().haveSimpleNameNotContaining("Test")
                .should().beAnnotatedWith(org.springframework.stereotype.Controller.class)
                .orShould().beAnnotatedWith(org.springframework.web.bind.annotation.RestController.class)
                .as("Controladores devem ser anotados com @Controller ou @RestController");

        regra.check(classesImportadas);
    }

    /**
     * Valida que Services têm a anotação @Service
     */
    @Test
    void servicosDevemSerAnotadosComService() {
        ArchRule regra = classes()
                .that().resideInAPackage("..service..")
                .and().haveSimpleNameContaining("Servico")
                .and().haveSimpleNameNotContaining("Test")
                .should().beAnnotatedWith(org.springframework.stereotype.Service.class)
                .as("Serviços devem ser anotados com @Service");

        regra.check(classesImportadas);
    }

    /**
     * Valida que Repositories não podem acessar Services
     */
    @Test
    void repositoriosNaoPodemAcessarServicos() {
        ArchRule regra = noClasses()
                .that().resideInAPackage("..repository..")
                .should().dependOnClassesThat()
                .resideInAPackage("..service..")
                .as("Repositórios não devem depender de Serviços");

        regra.check(classesImportadas);
    }

    /**
     * Valida que Repositories não podem acessar Controllers
     */
    @Test
    void repositoriosNaoPodemAcessarControladores() {
        ArchRule regra = noClasses()
                .that().resideInAPackage("..repository..")
                .should().dependOnClassesThat()
                .resideInAPackage("..controller..")
                .as("Repositórios não devem depender de Controladores");

        regra.check(classesImportadas);
    }

    /**
     * Valida que Services não podem acessar Controllers
     */
    @Test
    void servicosNaoPodemAcessarControladores() {
        ArchRule regra = noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat()
                .resideInAPackage("..controller..")
                .as("Serviços não devem depender de Controladores");

        regra.check(classesImportadas);
    }

    /**
     * Valida que Model não pode depender de outras camadas
     */
    @Test
    void modeloNaoDeveDependerDeOutrasCamadas() {
        ArchRule regra = noClasses()
                .that().resideInAPackage("..model..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..controller..", "..service..", "..repository..")
                .as("Modelo não deve depender de outras camadas");

        regra.check(classesImportadas);
    }

    /**
     * Valida que interfaces Repository estão no package repository
     */
    @Test
    void interfacesRepositoryDevemEstarNoPackageCorreto() {
        ArchRule regra = classes()
                .that().areInterfaces()
                .and().haveSimpleNameContaining("Repository")
                .or().haveSimpleNameContaining("Repositorio")
                .should().resideInAPackage("..repository..")
                .as("Interfaces de Repositório devem estar no package repository");

        regra.check(classesImportadas);
    }
}
