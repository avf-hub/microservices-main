

package store.laptop.inventory;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static store.laptop.inventory.ApplicationArchitectureTest.Layers.*;

class ApplicationArchitectureTest {
	@Test
	@DisplayName("The project structure should be implemented as a layered architecture.")
	void layeredArchitectureTest() {
		JavaClasses classes = new ClassFileImporter().importPackagesOf(InventoryServiceApplication.class);

		Architectures.layeredArchitecture()
			.layer(PRESENTATION.layerName()).definedBy(PRESENTATION.packageNames())
			.layer(BUSINESS.layerName()).definedBy(BUSINESS.packageNames())
			.optionalLayer(CONFIG.layerName()).definedBy(CONFIG.packageNames())

			.whereLayer(PRESENTATION.layerName()).mayNotBeAccessedByAnyLayer()
			.whereLayer(BUSINESS.layerName()).mayOnlyBeAccessedByLayers(PRESENTATION.layerName(),
				CONFIG.layerName())
			.whereLayer(CONFIG.layerName()).mayNotBeAccessedByAnyLayer()

			.check(classes);
	}

	enum Layers {
		PRESENTATION("Presentation", "..controller.."),
		BUSINESS("Business", "..domain..", "..domain.model..", "..domain.service.."),
		CONFIG("Config", "..config..");

		private final String layerName;
		private final String[] packageNames;

		Layers(String layerName, String... packageNames) {
			this.layerName = layerName;
			this.packageNames = packageNames;
		}

		public String layerName() {
			return layerName;
		}

		public String[] packageNames() {
			return packageNames;
		}
	}
}
