(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('UniteController', UniteController);

    UniteController.$inject = ['Unite', 'UniteSearch'];

    function UniteController(Unite, UniteSearch) {

        var vm = this;

        vm.unites = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Unite.query(function(result) {
                vm.unites = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            UniteSearch.query({query: vm.searchQuery}, function(result) {
                vm.unites = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
