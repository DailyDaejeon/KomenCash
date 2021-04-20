# frontend

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### settings.json
```
{
  "editor.quickSuggestions": {
    "strings": true
  },
  "editor.suggest.insertMode": "replace",
  "editor.codeActionsOnSave": {
    "source.fixAll.eslint": true
  },
  "eslint.alwaysShowStatus": true,
  "eslint.workingDirectories": [
    {
      "mode": "auto"
    }
  ],
  "eslint.validate": ["javascript"],
  "liveSassCompile.settings.formats": [
    {
      "format": "expanded",
      "extensionName": ".css",
      "savePath": "~/../css"
    }
  ],
  "liveSassCompile.settings.generateMap": false,
  "liveSassCompile.settings.excludeList": ["**/node_modules/**", ".vscode/**"],
  "liveSassCompile.settings.autoprefix": ["> 1%", "last 2 versions"]
}
```