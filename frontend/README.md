# Frontend Organization & Design System

## Architectural Role of This Folder

In this project, **Java Spring Boot with Thymeleaf** is our primary presentation and application engine.

Per Spring Boot conventions:
- **Active HTML Templates**: Located in [`backend/src/main/resources/templates/`](file:///c:/Users/abdul/OneDrive/Desktop/c%20programing%20langvage/Smart-Waste-Management/backend/src/main/resources/templates)
- **Active CSS Styles**: Located in [`backend/src/main/resources/static/css/`](file:///c:/Users/abdul/OneDrive/Desktop/c%20programing%20langvage/Smart-Waste-Management/backend/src/main/resources/static/css)
- **Active JavaScript Files**: Located in [`backend/src/main/resources/static/js/`](file:///c:/Users/abdul/OneDrive/Desktop/c%20programing%20langvage/Smart-Waste-Management/backend/src/main/resources/static/js)
- **Active Images & Icons**: Located in [`backend/src/main/resources/static/images/`](file:///c:/Users/abdul/OneDrive/Desktop/c%20programing%20langvage/Smart-Waste-Management/backend/src/main/resources/static/images)

### Purpose of `frontend/`:
To prevent duplication across the project, this folder holds:
1. **Design Specifications**: Wireframes, design tokens, style guide documentation, and color palettes.
2. **Raw Assets**: Master SVG illustrations, icon packs, and vector prototypes before optimization.
3. **Frontend Organization Guidelines**: Rules for component structuring and Thymeleaf layout fragments.

### Directory Breakdown:
- `html/`: Wireframe structures, UI layout specifications, and Thymeleaf fragment design plans.
- `css/`: Design tokens, typography guidelines, and responsive breakpoint documentation.
- `js/`: Client-side architecture guides (API fetch conventions, DOM handler guidelines).
- `assets/images/`: Raw branding artwork, high-resolution source graphics, and diagrams.
- `assets/icons/`: Raw SVG icon libraries and UI glyphs.
