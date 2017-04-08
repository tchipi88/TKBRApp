(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('EmployeFonctionController', EmployeFonctionController);

    EmployeFonctionController.$inject = ['EmployeFonction', 'EmployeFonctionSearch'];

    function EmployeFonctionController(EmployeFonction, EmployeFonctionSearch) {

        var vm = this;

        vm.employeFonctions = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            EmployeFonction.query(function(result) {
                vm.employeFonctions = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            EmployeFonctionSearch.query({query: vm.searchQuery}, function(result) {
                vm.employeFonctions = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
