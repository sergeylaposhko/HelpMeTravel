module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> */\n'
            },
            build: {
                src: ['app/script/*/*.js', '!app/build/*'],
                dest: 'app/build/dist.min.js'
            }
        },
        watch: {
            scripts: {
                files: ['app/script/*/*.js', 'app/script/*/*.css'],
                tasks: ['uglify', 'concat', 'cssmin'],
                options: {
                    spawn: false
                }
            }
        },
        cssmin: {
            css: {
                src: 'app/build/dist.css',
                dest: 'app/build/dist.min.css'
            }
        },
        concat: {
            css: {
                src: 'app/script/*/*.css',
                dest: 'app/build/dist.css'
            }
        },

    });

    // Load the plugin that provides the "uglify" task.
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-css');
    grunt.loadNpmTasks('grunt-contrib-concat');

    // Default task(s).
    grunt.registerTask('default', ['uglify']);

};