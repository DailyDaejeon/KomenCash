module.exports = {
  root: true,
  env: {
    node: true
  },
  'extends': [
    'plugin:vue/essential',
    'eslint:recommended'
  ],
  parserOptions: {
    parser: 'babel-eslint'
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    // Parsing error: unexpected-character-in-unquoted-attribute-value  vue/no-parsing-error
    // 위에러 방지용
    'vue/no-parsing-error': [2, { 'x-invalid-end-tag': false }]
  }
}